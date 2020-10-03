#Hy335b

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
from Crypto.Hash import SHA




#Functions

def ping_server(hostname, iterations):
	i = 0
	average_list = []
	pingmono = []
	pingresult = []
	average = []
	
	#
	filename = "pingios" + str(thread_counter) +".txt"
	#
	
	for i in range(0,iterations):
		
		response = os.system("ping -c 1 " + hostname + " >> " + filename )

		#and then check the response...
		if response != 0:
			#print hostname, 'is down!'
			print 'server can not be reached'
			return -1
		  


		
		with open(filename) as zfile:
			for grammi in zfile.readlines():
				ylist = grammi.split()
				pingmono.append(ylist)

		pingresult = pingmono[-1]
		average = pingresult[3]
		#print average	#Uncomment to see Live PINGZ

		average1 = average.split('/')
		average_list.append(average1[1])
		
		
		os.remove(filename)
	
	sum = 0
	i = 0
	for i in range(iterations):
		sum += float(average_list[i])
	sum /= iterations
	return sum
	
	
	
def trace_server(hostname):
	trace = []
	trace_results = []
	hops = []
	
	#
	filename = "traceroute" + str(thread_counter) + ".txt"
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

	
def download_file (file_url, filename):
	elapsed = -1
	start = time.time()
	try:
		urllib.urlretrieve(file_url, filename)
		elapsed = time.time() - start
	except IOError:
		pass
	return elapsed		

	
def encrypt_file (filename, key, mode, iv):
	
	data = ''
	with open(filename, "rb") as f_obj:
		buffer = f_obj.read(1024)
		while buffer != '':
			data += buffer
			buffer = f_obj.read(1024)
			
	cipher = AES.new(key, mode, iv)
	encrypted_data = iv + cipher.encrypt( data)
	
	return encrypted_data
	
	
	
#
	
	
#Threads
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
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	def setConnection(self, conn, client_key):
		self.conn = conn
		self.client_key = client_key
	def run(self):
		#print "Starting " + self.name
		
		#response = "\n" + self.name + " " + self.hostname + ":: RTT: " + str(ping_server(self.hostname, self.iterations))	#Uncomment to see relay Threads in action
		response = self.hostname + " :: RTT: " + str(ping_server(self.hostname, self.iterations))
		
		enc_response = self.client_key.encrypt(response, 32)
		
		self.conn.send(enc_response[0])
		#self.conn.send("\n" + self.name + " " + self.hostname + ":: RTT: " + str(ping_server(self.hostname, self.iterations)))
		self.conn.close()
		#print "\n" + self.name + " " + self.hostname + ":: RTT: " + str(ping_server(self.hostname, self.iterations))
		#print "Exiting " + self.name


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
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	def setConnection(self, conn, client_key):
		self.conn = conn
		self.client_key = client_key
	def run(self):
		#print "Starting " + self.name
		#response = "\n" + self.name + " " + self.hostname + ":: Hops: " + str(trace_server(self.hostname))	#Uncomment to see relay Threads in action
		response = self.hostname + " :: Hops: " + str(trace_server(self.hostname))
		
		
		enc_response = self.client_key.encrypt(response, 32)
		
		self.conn.send(enc_response[0])
		
		#self.conn.send("\n" + self.name + " " + self.hostname + ":: Hops: " + str(trace_server(self.hostname)))
		self.conn.close()
		#print "\n" + self.name + " " + self.hostname + ":: Hops: " + str(trace_server(self.hostname))
		#print "Exiting " + self.name	

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
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	def setConnection(self, conn, client_key):
		self.conn = conn
		self.client_key = client_key
	def run(self):
		#print "Starting " + self.name
		
		#response = "\n" + self.name + " " + self.hostname + ":: Hops: " + str(trace_server(self.hostname)) + " || RTT: " + str(ping_server(self.hostname, self.iterations))	#Uncomment to see relay Threads in action
		response = self.hostname + " :: Hops: " + str(trace_server(self.hostname)) + " || RTT: " + str(ping_server(self.hostname, self.iterations))
		
		enc_response = self.client_key.encrypt(response, 32)
		
		self.conn.send(enc_response[0])
		
		#self.conn.send("\n" + self.name + " " + self.hostname + ":: Hops: " + str(trace_server(self.hostname)) + " || RTT: " + str(ping_server(self.hostname, self.iterations)))
		self.conn.close()
		#print "\n" + self.name + " " + self.hostname + ":: Hops: " + str(trace_server(self.hostname)) + " || RTT: " + str(ping_server(self.hostname, self.iterations))
		#print "Exiting " + self.name
	
	
	
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
		
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	def setExtension(self, extension):
		self.extension = extension
	def setConnection(self, conn, client_key):
		self.conn = conn
		self.client_key = client_key
	def run(self):
		
		global private_key
		
		response = ''
		
		
		elapsed_time = download_file( self.hostname, self.extension)
		
		if elapsed_time == -1:
			response =  "failure"
			enc_response = self.client_key.encrypt(response, 32)
			self.conn.send(enc_response[0])
		else:
			response = "success"
			enc_response = self.client_key.encrypt(response, 32)
			self.conn.send(enc_response[0])
			
			
			cipher_key = ''.join(random.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for _ in range(16))
			cipher_mode = AES.MODE_CFB
			cipher_blocksize = Random.new().read(AES.block_size)
			
			input = self.conn.recv(1024)
			message = private_key.decrypt(input)
			if message != "ok":
				raise socket.timeout("")
				
			#send Key
			enc_response = self.client_key.encrypt(cipher_key, 32)
			self.conn.send(enc_response[0])
			
			input = self.conn.recv(1024)
			message = private_key.decrypt(input)
			if message != "ok":
				raise socket.timeout("")
				
			#send mode
			enc_response = self.client_key.encrypt(str(cipher_mode), 32)
			self.conn.send(enc_response[0])
			
			input = self.conn.recv(1024)
			message = private_key.decrypt(input)
			if message != "ok":
				raise socket.timeout("")
			
			#send block_size
			enc_response = self.client_key.encrypt(cipher_blocksize, 32)
			self.conn.send(enc_response[0])
				
			input = self.conn.recv(1024)
			message = private_key.decrypt(input)
			if message != "ok":
				raise socket.timeout("")
				
			
			encrypted_file = encrypt_file(self.extension, cipher_key, cipher_mode, cipher_blocksize)
			
			self.conn.send(encrypted_file)
			
			
			
			
			
			
			
			'''
			
			#$Working mode without encryption
			data = ''
			with open(self.extension, "rb") as f_obj:	#Encoding and sending file
				buffer = f_obj.read(1024)
				while buffer != '':
					data += buffer
					buffer = f_obj.read(1024)
				
			self.conn.send(data)
			#$
			'''
			
			
			#enc_response = self.client_key.encrypt(response, 32)
			#self.conn.send(enc_response[0])
			
			
			'''
			fl = open(self.extension, "rb")		#Encoding and sending file
			
			line = fl.read(buffsize);
			while line != '':
				enc_response = self.client_key.encrypt(line, 32)
				#print len(line)
				#print len(enc_response[0])
				#print "\n"
				#print enc_response[0]
				self.conn.send(enc_response[0])
				check = self.conn.recv(1024)
				check = private_key.decrypt(check)
				if check != "ok":
					print "There was an error in file transfer"
					break
				
				line = fl.read(buffsize);
			'''
			
			'''
				line = f_obj.read(buffsize)
				while line != '':
					enc_response = self.client_key.encrypt(line, 32)
					#print len(line)
					#print len(enc_response[0])
					#print "\n"
					#print enc_response[0]
					self.conn.send(enc_response[0])
					check = self.conn.recv(1024)
					check = private_key.decrypt(check)
					if check != "ok":
						print "There was an error in file transfer"
						break
					
					line = f_obj.read(buffsize)
			'''
			'''
				for line in f_obj:
					enc_response = self.client_key.encrypt(line, 32)
					self.conn.send(enc_response[0])
					#self.conn.send(line)
			'''
		
		
		#Encode file
		
		#send File
		
		
		
		self.conn.close()
		
	
	
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
		
		client_socket.bind((ip, port))
			
		client_socket.settimeout(4)
		
		print "\n" + hostname + ": Relay up, Awaiting Connections..\n"
		
		while close == False:
		
			try:
				
				client_socket.listen(5)
					
				while True:
				
					conn, addr = client_socket.accept()     # Establish connection with client.
				
					
					print ("Connection established with: " +  socket.gethostbyname(str(addr[1])))
					
					#conn.recv(1024)
					
					
					#Thread Dispatch + state machine
					command = ''
					iteration_number = 0
					server = ''
					
					
					input = conn.recv(1024)
					#print input
					
					client_key = RSA.importKey(input, passphrase = None)	#receive clients public key
					
					
					
					#send = private_key.publickey().exportKey(format = 'PEM', passphrase = None, pkcs = 1)
					#print len(send)
					#conn.send(send)
					conn.send(private_key.publickey().exportKey(format = 'PEM', passphrase = None, pkcs = 1)) #send public key
					
					
					input = conn.recv(1024)
					
					
					response = private_key.decrypt(input)
					
					
					temp = response.split(',')
					
					server = temp[0]
					iteration_number = temp[1]
					command = temp[2]
					
					
					
					if len(temp) != 3:
						raise socket.timeout("")
						
					
					
					if (command == "ping"):
			
						serverindex = dns.index(server)
						iteration_number = int(iteration_number)
						thread1 = ping_Thread(1, "Thread-" + str(thread_counter), 1)
						thread_counter += 1
						thread1.setHostname(dns[serverindex])
						thread1.setIterations(iteration_number)
						thread1.setConnection(conn, client_key)
						thread_list.append(thread1)
						thread1.start()
						
					elif ((command == "traceroute") or (command == "trace")):
						
						serverindex = dns.index(server)
						thread1 = trace_Thread(1, "Thread-" + str(thread_counter), 1)
						thread_counter += 1
						thread1.setHostname(dns[serverindex])
						thread1.setConnection(conn, client_key)
						thread_list.append(thread1)
						thread1.start()
				
					elif (command == "latency"):
						serverindex = dns.index(server)
						iteration_number = int(iteration_number)
						thread1 = latency_Thread(1, "Thread-" + str(thread_counter), 1)
						thread_counter += 1
						thread1.setHostname(dns[serverindex])
						thread1.setIterations(iteration_number)
						thread1.setConnection(conn, client_key)
						thread_list.append(thread1)
						thread1.start()
					
					elif (command == "download"):
						serverindex = downloads.index(server)
						thread1 = download_Thread(1, "Thread-" + str(thread_counter), 1)
						thread_counter += 1
						thread1.setHostname(downloads[serverindex])
						thread1.setExtension(extensions[serverindex])
						thread1.setConnection(conn, client_key)
						thread_list.append(thread1)
						thread1.start()
						
					#client_socket.settimeout(4)
					#conn.send('Thank you for connecting')
					#conn.close()
					
			except socket.timeout:
				client_socket.settimeout(4)
				#print "timeout"
				pass
			except IndexError:
				#client_socket.settimeout(4)
				#print "timeout"
				pass
		  
		client_socket.close()
		#print "Starting " + self.name
		#print "Exiting " + self.name
	
		
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


random_generator = ''
private_key = ''
public_key = ''

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

random_generator = Random.new().read
private_key = RSA.generate(1024, random_generator)

if( not (private_key.can_encrypt() and private_key.can_sign() and private_key.has_private())):
	print "An unexpected error occurred while generating private key"
	sys.exit()

public_key = private_key.publickey()

ip = socket.gethostbyname(socket.gethostname())
hostname = socket.gethostname()

hash = SHA.new(hostname)


port = find_port(hostname)

port = int(port)

if port == -1:
	sys.exit("Host could not be found in relays file")
	
relayThread = relay_Thread(1, "Thread-" + str(thread_counter), 1)
thread_counter += 1
thread_list.append(relayThread)
relayThread.start()

input = ''

while input != "exit":
	input = raw_input( hostname + ": Type 'exit' to exit \n")

close = True


sys.exit()

	


