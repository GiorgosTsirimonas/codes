#Hy335b

#Amiridis Georgios		- 2993 - amirid@csd.uoc.gr
#Tsirimonas Georgios	- 2977 - tsirimon@csd.uoc.gr

#imports
import sys
import threading
import os
import urllib
import socket
import time
import random, string

from urllib2 import URLError
from Crypto.PublicKey import RSA
from Crypto import Random
from Crypto.Cipher import AES
from Crypto.Hash import SHA512
from Crypto.Signature import PKCS1_v1_5




#Functions


#Function that pings an end server and returns the average rtt or -1 on failure
def ping_server(hostname, iterations):
	last = ''
	line = ''
	average = ''
	filename = "pingios" + str(random.randint(1,10001)) + ".txt"	#Generate random filename 
	response = os.system("ping -c " + str(iterations) + " " + hostname + " >> " + filename )	#Send signal to OS to ping
	if response != 0:	#Check response for errors
		os.remove(filename)
		#print "Server " + hostname + " can not be reached"	#Uncomment to display
		return -1
	
	try:
		with open(filename) as fileru:	#Open the result file and parse it to retrieve the avg RTT
			line = fileru.readline()
			while line != '':
				#print "L: " + line
				last = line
				line = fileru.readline()
		fileru.close()
		os.remove(filename)
		
	except OSError, IOError:
		print "There was an error reading the ping file"
		os.remove(filename)
		return -1
		
	line = last.split("/")
	average = line[4]
	#print average
	
	return average #return -1	
	
	
	
#Function that performs a traceroute to an end server and returns the number of hops or -1 on failure	
def trace_server(hostname):
	trace = []
	trace_results = []
	hops = []
	
	#
	filename = "traceroute" + str(random.randint(1,10001)) + ".txt"	#Generate random filename 
	#filename = "traceroute" + str(thread_counter) + ".txt"
	#

	#traceroute = os.system("traceroute -w 150 " + hostname + " >> " + filename)
	traceroute = os.system("traceroute " + hostname + " >> " + filename)
	
	with open(filename) as mfile:
		for line in mfile.readlines():
			mylist = line.split()
			trace.append(mylist)

	trace_results = trace[-1]
	hops = trace_results[0]	
	os.remove(filename)
	
	return hops


	
#Function that downloads a file	
def download_file (file_url, filename):
	elapsed = -1
	start = time.time()
	try:
		urllib.urlretrieve(file_url, filename)
		elapsed = time.time() - start
	except IOError:
		pass
	return elapsed		

	
	
def encrypt_file (filename, key, mode, iv, private_key):
	
	sha_signature = get_sha512_signature(private_key)
	
	data = ''
	with open(filename, "rb") as f_obj:
		buffer = f_obj.read(1024)
		while buffer != '':
			data += buffer
			buffer = f_obj.read(1024)
		data += sha_signature	#Sign File	
	cipher = AES.new(key, mode, iv)
	encrypted_data = iv + cipher.encrypt( data)	#encrypt file
	
	return encrypted_data

	
	
#Function that given a private key and a hash generates a signature
def get_sha512_signature (private_key):
	global hash
	signer = PKCS1_v1_5.new(private_key)
	#signature = signer.sign(hash)
	return signer.sign(hash)

	
#Function that returns the signature
def get_signature ():
	global hostname
	return str(hostname)	
	
	

#Function that given a public key and  a signature validates the signature: returns true if it is valid , false otherwise
def verify_signature (public_key, signature):	
	global hash
	#signer = PKCS1_v1_5.new(public_key)
	verifier = PKCS1_v1_5.new(public_key)
	
	if verifier.verify(hash, signature):
		return True
	else:
		return False

		

#Function that checks the signature
def check_signature (signature):
	global hostname
	#print str(signature) + "==" + str(hostname)
	return str(signature) == str(hostname)
#
	
	
	
#Threads


#Thread with the purpose of pinging an end server and transmitting the data to a client
class ping_Thread (threading.Thread):
	
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
		self.hostname = ''
		self.iterations = ''
		self.conn = None
		self.client_key = None
		self.private_key = ''
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	def setConnection(self, conn, client_key):
		self.conn = conn
		self.client_key = client_key
	def setPrivate_key(self, private_key):
		self.private_key = private_key
	def run(self):
		
		#response = "\n" + self.name + " " + self.hostname + ":: RTT: " + str(ping_server(self.hostname, self.iterations))	#Uncomment to see relay Threads in action
		response = self.hostname + " RTT:" + str(ping_server(self.hostname, self.iterations)) + ":," + get_signature()
		enc_response = self.client_key.encrypt(response, 32)
		self.conn.send(enc_response[0])
		self.conn.close()
		


#Thread with the purpose of running a traceroute to an end server and transmitting the number of hops to a client
class trace_Thread (threading.Thread):
	
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
		self.hostname = ''
		self.iterations = ''
		self.conn = None
		self.client_key = None
		self.private_key = ''
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	def setConnection(self, conn, client_key):
		self.conn = conn
		self.client_key = client_key
	def setPrivate_key(self, private_key):
		self.private_key = private_key
	def run(self):
		#response = "\n" + self.name + " " + self.hostname + ":: Hops: " + str(trace_server(self.hostname))	#Uncomment to see relay Threads in action
		response = self.hostname + " Hops:" + str(trace_server(self.hostname)) + ":," + get_signature()
		enc_response = self.client_key.encrypt(response, 32)
		self.conn.send(enc_response[0])
		self.conn.close()
		
		

#Thread with the purpose of running a combination of pings and traceroute to and end server and transmitting the average rtt and number of hops to a client
class latency_Thread (threading.Thread):
	
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
		self.hostname = ''
		self.iterations = ''
		self.conn = None
		self.client_key = None
		self.private_key = ''
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	def setConnection(self, conn, client_key):
		self.conn = conn
		self.client_key = client_key
	def setPrivate_key(self, private_key):
		self.private_key = private_key
	def run(self):
		
		#response = "\n" + self.name + " " + self.hostname + ":: Hops: " + str(trace_server(self.hostname)) + " || RTT: " + str(ping_server(self.hostname, self.iterations))	#Uncomment to see relay Threads in action
		response = self.hostname + " Hops:" + str(trace_server(self.hostname)) + ": RTT:" + str(ping_server(self.hostname, self.iterations)) + ":," + get_signature()
		enc_response = self.client_key.encrypt(response, 32)
		self.conn.send(enc_response[0])
		self.conn.close()
		
	

#Thread with the purpose of directly downloading a specified file encrypting it with an AES symmetric encryption key, signing it with a signature and transmitting it to a client along with the key
class download_Thread (threading.Thread):
	
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
		self.hostname = ''
		self.iterations = ''
		self.extension = ''
		self.conn = None
		self.client_key = None
		self.private_key = ''
		
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	def setExtension(self, extension):
		self.extension = extension
	def setConnection(self, conn, client_key):
		self.conn = conn
		self.client_key = client_key
	def setPrivate_key(self, private_key):
		self.private_key = private_key
	def run(self):
		
		response = ''
		elapsed_time = download_file( self.hostname, self.extension)
		
		if elapsed_time == -1:
			response =  "failure," + get_signature()
			enc_response = self.client_key.encrypt(response, 32)
			self.conn.send(enc_response[0])
		else:
			response = "success," + get_signature()
			enc_response = self.client_key.encrypt(response, 32)
			self.conn.send(enc_response[0])
			
			cipher_key = ''.join(random.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for _ in range(16))
			cipher_mode = AES.MODE_CFB
			cipher_blocksize = Random.new().read(AES.block_size)
			
			input = self.conn.recv(1024)
			message = self.private_key.decrypt(input)
			message_ar = message.split(",")
			if message_ar[0] != "ok" or not check_signature(message_ar[1]):
				print self.name + " Signal missmatch"
				raise socket.timeout("")
				
			#send Key
			enc_response = self.client_key.encrypt(cipher_key, 32)
			self.conn.send(enc_response[0])
			
			input = self.conn.recv(1024)
			message = self.private_key.decrypt(input)
			message_ar = message.split(",")
			if message_ar[0] != "ok" or not check_signature(message_ar[1]):
				print self.name + " Signal missmatch"
				raise socket.timeout("")
				
			#send mode
			enc_response = self.client_key.encrypt(str(cipher_mode), 32)
			self.conn.send(enc_response[0])
			
			input = self.conn.recv(1024)
			message = self.private_key.decrypt(input)
			message_ar = message.split(",")
			if message_ar[0] != "ok" or not check_signature(message_ar[1]):
				print self.name + " Signal missmatch"
				raise socket.timeout("")
			
			#send block_size
			enc_response = self.client_key.encrypt(cipher_blocksize, 32)
			self.conn.send(enc_response[0])
				
			input = self.conn.recv(1024)
			message = self.private_key.decrypt(input)
			message_ar = message.split(",")
			if message_ar[0] != "ok" or not check_signature(message_ar[1]):
				print self.name + " Signal missmatch"
				raise socket.timeout("")
				
			
			encrypted_file = encrypt_file(self.extension, cipher_key, cipher_mode, cipher_blocksize, self.private_key)
			
			self.conn.send(encrypted_file)
			
		
		self.conn.close()
		
	
	
#Thread that acts as a "Connection broker" listening for incoming client connections, exchanging RSA keys with them and then starting other threads according to the clients commands
class relay_Thread (threading.Thread):

	global hostname
	global ip
	global port
	global client_socket
	global close
	global thread_counter
	
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
	
	def run(self):
	
		global thread_counter
		global hostname
		
		client_socket.bind((ip, port))
			
		client_socket.settimeout(4)
		
		print "\n" + hostname + ": Relay up, Awaiting Connections..\n"
		
		while close == False:
		
			try:
				client_socket.listen(5)
					
				while True:
				
					conn, addr = client_socket.accept()     # Establish connection with client.
				
					print ("Connection established with: " +  socket.gethostbyname(str(addr[1])))
					
					#Generate RSA private key
					random_generator = Random.new().read
					private_key = RSA.generate(1024, random_generator)	#Generate RSA cipher key
					if( not (private_key.can_encrypt() and private_key.can_sign() and private_key.has_private())):	#Check key for errors
						print "An unexpected error occurred while generating private key"
						sys.exit()

					public_key = private_key.publickey()
					
					#Thread Dispatch + state machine
					command = ''
					iteration_number = 0
					server = ''
					
					input = conn.recv(1024)	#Receive clients public key
					
					client_key = RSA.importKey(input, passphrase = None)	#Import clients public key
					
					conn.send(private_key.publickey().exportKey(format = 'PEM', passphrase = None, pkcs = 1)) #Send public key
					
					input = conn.recv(1024)	#Receive command
					response = private_key.decrypt(input)	#Decrypt command
					
					temp = response.split(',')	#Format command
					
					server = temp[0]
					iteration_number = temp[1]
					command = temp[2]
					client_signature = temp[3]
					
					if not check_signature(client_signature):
						print hostname + "::" + client_signature
						break
					
					
					if len(temp) != 4:	#evaluate command
						print "Invalid Command"
						raise socket.timeout("")
						
					if (command == "ping"):
			
						serverindex = dns.index(server)
						iteration_number = int(iteration_number)
						thread1 = ping_Thread(1, "Thread-" + str(thread_counter), 1)
						thread_counter += 1
						thread1.setHostname(dns[serverindex])
						thread1.setIterations(iteration_number)
						thread1.setConnection(conn, client_key)
						thread1.setPrivate_key(private_key)
						#thread_list.append(thread1)
						thread1.start()
						
					elif ((command == "traceroute") or (command == "trace")):
						
						serverindex = dns.index(server)
						thread1 = trace_Thread(1, "Thread-" + str(thread_counter), 1)
						thread_counter += 1
						thread1.setHostname(dns[serverindex])
						thread1.setConnection(conn, client_key)
						thread1.setPrivate_key(private_key)
						#thread_list.append(thread1)
						thread1.start()
				
					elif (command == "latency"):
						serverindex = dns.index(server)
						iteration_number = int(iteration_number)
						thread1 = latency_Thread(1, "Thread-" + str(thread_counter), 1)
						thread_counter += 1
						thread1.setHostname(dns[serverindex])
						thread1.setIterations(iteration_number)
						thread1.setConnection(conn, client_key)
						thread1.setPrivate_key(private_key)
						#thread_list.append(thread1)
						thread1.start()
					
					elif (command == "download"):
						serverindex = downloads.index(server)
						thread1 = download_Thread(1, "Thread-" + str(thread_counter), 1)
						thread_counter += 1
						thread1.setHostname(downloads[serverindex])
						thread1.setExtension(extensions[serverindex])
						thread1.setConnection(conn, client_key)
						thread1.setPrivate_key(private_key)
						#thread_list.append(thread1)
						thread1.start()
						
					#client_socket.settimeout(4)
					#conn.send('Thank you for connecting')
					#conn.close()
					
			except socket.timeout:
				client_socket.settimeout(4)
				pass
			except IndexError:
				pass
		  
		client_socket.close()
		
	
		
#List checking
def find_port(hostname):	
	global relays
	global relays_port
	i = 0
	for c in relays:
		if relays[i] == hostname:
			return relays_port[i]
		i += 1
	return -1

	

endservers_filename = ''
relaynodes_filename = ''
downloads_filename = 'files2download.txt'	#Filename containing files to download (Hardcoded against my better judgement) 

dns = []	#DNS names of the end servers
names = []	#Local names for the end servers
relays = []	#Relay server Names
relays_ip = []	#Relay server IPs
relays_port = []	#Relay server ports

hostname = ''
ip = ''
port = 0

thread_counter = 0
thread_list = []

downloads = []	#Files to download
extensions = []	#Files to download extensions

client_socket = socket.socket()

close = False

hash = ''


#Command line Argument Processing

if sys.argv[1] == '-e':
	endservers_filename = sys.argv[2]
elif sys.argv[1] == '-r':
	relaynodes_filename = sys.argv[2]
else:
	sys.exit("Command line arguments not valid!")
	
if sys.argv[3] == '-e':
	endservers_filename = sys.argv[4]
elif sys.argv[3] == '-r':
	relaynodes_filename = sys.argv[4]
else:
	sys.exit("Command line arguments not valid!")
#


#Decoding


#Opening File and reading and distributing the names in 2 lists 
with open(endservers_filename) as f_obj:
	for line in f_obj:
		i = 0
		text = ''
		
		while line[i] != ',':
			text  = text + line[i]
			i = i + 1
			
		i = i + 2
		dns.append(text)
		text = ''
		
		while (i < len(line)) and (line[i] != '\n') and (line[i] != '\r'):
			text  = text + line[i]
			i = i + 1
			
		names.append(text)
		
		
with open(relaynodes_filename) as f_obj:
	for line in f_obj:
		i = 0
		text = ''
		
		while line[i] != ',':
			text  = text + line[i]
			i = i + 1
			
		i = i + 2
		relays.append(text)
		text = ''
		
		while line[i] != ',':
			text  = text + line[i]
			i = i + 1
			
		i = i + 2
		relays_ip.append(text)
		text = ''
		
		while (i < len(line)) and (line[i] != '\n') and (line[i] != '\r'):
			text  = text + line[i]
			i = i + 1
			
		relays_port.append(text)

			
with open(downloads_filename) as f_obj:
	for line in f_obj:
		text = ''
		extension = ''
		i = 0
		while (line[i] != '\r' and line[i] != '\n'):
			if line[i] == '/':
				extension = ''
			else:
				extension += line[i]
			text += line[i]
			i += 1
		downloads.append(text)
		extensions.append(extension)	
#


#Main


ip = socket.gethostbyname(socket.gethostname())
hostname = socket.gethostname()

hash = SHA512.new(str(hostname))

port = find_port(hostname)

port = int(port)

if port == -1:
	sys.exit("Host could not be found in relays file")
	
relayThread = relay_Thread(1, "Thread-" + str(thread_counter), 1)
thread_counter += 1
#thread_list.append(relayThread)
relayThread.start()

input = ''

while input != "exit":	#State Machine entry
	input = raw_input( hostname + ": Type 'exit' to exit \n")

close = True	#Signal relaythread to end
relayThread.join()	#wait for relaythread to end


sys.exit()

