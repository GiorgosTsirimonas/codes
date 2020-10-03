#Hy335b

#imports
import sys
import threading
import os
import urllib
import socket
import time

from urllib2 import URLError
from Crypto.PublicKey import RSA
from Crypto import Random

from Crypto.Cipher import AES


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
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	
	def run(self):
		#print "Starting " + self.name
		print "\n" + self.name + " " + self.hostname + " :: RTT: " + str(ping_server(self.hostname, self.iterations))
		#print "Exiting " + self.name


class trace_Thread (threading.Thread):
	
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
		self.hostname = ''
		self.iterations = ''
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	
	def run(self):
		#print "Starting " + self.name
		print "\n" + self.name + " " + self.hostname + " :: Hops: " + str(trace_server(self.hostname))
		#print "Exiting " + self.name	

class latency_Thread (threading.Thread):
	
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
		self.hostname = ''
		self.iterations = ''
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	
	def run(self):
		#print "Starting " + self.name
		print "\n" + self.name + " " + self.hostname + " :: Hops: " + str(trace_server(self.hostname)) + " || RTT: " + str(ping_server(self.hostname, self.iterations))
		#print "Exiting " + self.name
	

class relay_Thread (threading.Thread):
	global relays
	global relays_ip
	global relays_port
	
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
		self.hostname = ''
		self.iterations = ''
		self.command = ''
		self.iterator = -1
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	def setCommand(self, command):
		self.command = command
	def setIterator(self, iterator):
		self.iterator = iterator
		
	def run(self):
	
		global private_key
		global public_key
		
		thread_socket = socket.socket()
		
		thread_socket.settimeout(20)
		
		try:
			thread_socket.connect( (relays_ip[self.iterator], int(relays_port[self.iterator])))
			
			print "Connection established with: " + relays[self.iterator]
			
			#Exchange Keys
			#thread_socket.send(public_key)	#send public key
			thread_socket.send(private_key.publickey().exportKey(format = 'PEM', passphrase = None, pkcs = 1)) 	#send public key
			
			relay_message = thread_socket.recv(1024)
			#print relay_message
			
			relay_public_key = RSA.importKey(relay_message, passphrase = None)	#receive relays public key
			
			message = self.hostname + "," + str(self.iterations) + "," + self.command
			
			encrypted_message = relay_public_key.encrypt( message, 32)	#Encrypting message
			
			thread_socket.send(encrypted_message[0])	#send command
			
			#thread_socket.send(self.hostname + "," + str(self.iterations) + "," + self.command)
			
			relay_message = thread_socket.recv(1024)	#get response
			
			response = private_key.decrypt(relay_message)	#decrypt response
			
			response = "\n" + self.name + " " + response#
			
			print response#
			
			#print thread_socket.recv(1024)
			
		except socket.error:
			#print "\n" + self.name + " Connection to " + relays[self.iterator] +" could not be established"	#uncomment to see failed connection attempts
			pass
			
		finally:	
			thread_socket.close()
	
	
class download_Thread (threading.Thread):
	
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
		self.hostname = ''
		self.files_counter = 0
		self.extension = ''
	def setHostname(self, hostname):
		self.hostname = hostname
	def setExtension(self, extension):
		self.extension = extension
	def run(self):
		elapsed_time = download_file( self.hostname, self.extension)
		
		if elapsed_time == -1:
			print "\n" + self.name + " file download failed, file could not be found"
		else:
			print "\n" + self.name + " file download complete filename: " + self.extension
	

class relay_download_Thread (threading.Thread):
	global relays
	global relays_ip
	global relays_port
	
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
		self.iterations = ''
		self.hostname = ''
		self.extension = ''
		self.command = "download"
		self.iterator = -1
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterator(self, iterator):
		self.iterator = iterator
	def setExtension(self, extension):
		self.extension = extension
		
	def run(self):
	
		global private_key
		global public_key
		
		thread_socket = socket.socket()
		
		thread_socket.settimeout(20)
		
		try:
			thread_socket.connect( (relays_ip[self.iterator], int(relays_port[self.iterator])))
			
			print "Connection established with: " + relays[self.iterator]
			
			#Exchange Keys
			#thread_socket.send(public_key)	#send public key
			thread_socket.send(private_key.publickey().exportKey(format = 'PEM', passphrase = None, pkcs = 1)) 	#send public key
			
			relay_message = thread_socket.recv(1024)
			#print relay_message
			
			relay_public_key = RSA.importKey(relay_message, passphrase = None)	#receive relays public key
			
			message = self.hostname + "," + str(self.iterations) + "," + self.command
			
			encrypted_message = relay_public_key.encrypt( message, 32)	#Encrypting message
			
			thread_socket.send(encrypted_message[0])	#send command
			
			#thread_socket.send(self.hostname + "," + str(self.iterations) + "," + self.command)
			
			relay_message = thread_socket.recv(1024)	#get response
			
			
			response = private_key.decrypt(relay_message)	#decrypt response
			
			
			#decide where to download based on response
			
			#download
			
			#decrypt
			
			#save in drive
			if response == "success":
				
				
				'''
				received_file_data = ''
				confirmation_message = relay_public_key.encrypt( "ok", 32)
				
				buffer = ''
				
				
				while True:
					relay_message = thread_socket.recv(1024)
					if not relay_message:
						break
					buffer += private_key.decrypt(relay_message)
					thread_socket.send(confirmation_message[0])
				'''
				
				
				'''
			
				while True:	#Receiving file
					relay_message = thread_socket.recv(1024)
					if not relay_message:
						break
					received_file_data += relay_message
					#response += relay_message
					
				i = 0
				j = 0
				buffer = ''
				response = ''
				limit = len (received_file_data)
				while i < limit:
					
					buffer += received_file_data[i]
					i += 1
					j += 1
					if j == 128:
						j = 0
						#print buffer
						print len(buffer)
						#response += private_key.decrypt(buffer)
						buffer = ''
					
				'''	
				
				ok_response = relay_public_key.encrypt("ok", 32)
				thread_socket.send(ok_response[0])
				
				cipher_key = ''
				cipher_mode = ''
				cipher_blocksize = ''
				
				relay_message = thread_socket.recv(1024)		#get response
				response = private_key.decrypt(relay_message)	#decrypt response
				cipher_key = response
				
				thread_socket.send(ok_response[0])
				
				relay_message = thread_socket.recv(1024)		#get response
				response = private_key.decrypt(relay_message)	#decrypt response
				cipher_mode = int(response)
				
				thread_socket.send(ok_response[0])
				
				relay_message = thread_socket.recv(1024)		#get response
				response = private_key.decrypt(relay_message)	#decrypt response
				cipher_blocksize = response
				
				thread_socket.send(ok_response[0])
				
				buffer = ''
				while True:	#Receiving file
					relay_message = thread_socket.recv(1024)
					if not relay_message:
						break
					buffer += relay_message
					
				cipher = AES.new(cipher_key, cipher_mode, cipher_blocksize)
				
				decrypted_file = cipher.decrypt(buffer)[16:]
				
				with open(self.extension, 'wb') as the_file:
					the_file.write(decrypted_file)
					#the_file.write(buffer)
				
				
				'''
				#$Working mode without encryption
				buffer = ''
				while True:	#Receiving file
					relay_message = thread_socket.recv(1024)
					if not relay_message:
						break
					buffer += relay_message
					#response += relay_message
					
				with open(self.extension, 'wb') as the_file:
					the_file.write(buffer)
				
				#$
				'''
				
				response = "\n" + self.name + " File '" + self.extension + "' downloaded successfully"
				
			elif response == "failure":
				response = "\n" + self.name + " " + response#
			else:
				response = "\n" + self.name + " No response from relay"#
			
			#Currently just printing
			
			print response#
			
			#--
			
		except socket.error:
			#print "\n" + self.name + " Connection to " + relays[self.iterator] +" could not be established"	#uncomment to see failed connection attempts
			pass
			
		finally:	
			thread_socket.close()

def select_mode():

	global mode
	
	mode = 'N'
	#User State Machine
	while mode == 'N':
		print "Modes: 'D' (Direct Mode), 'R' (Relay Mode)"
		
		#change Before Release
		temp = raw_input("Select Mode: ")
		#temp = 'D'

		if temp == 'D':
			mode = 'D'
		elif temp == 'R':
			mode = 'R'
		else:
			print "Please Input a Valid Mode: "

#


#Globals

endservers_filename = ''
relaynodes_filename = ''
downloads_filename = 'files2download.txt'	#Filename containing files to download (Hardcoded against my better judgement) 

dns = []	#DNS names of the end servers
names = []	#Local names for the end servers
relays = []	#Relay server Names
relays_ip = []	#Relay server IPs
relays_port = []	#Relay server ports
downloads = []	#Files to download
extensions = []	#Files to download extensions
thread_list = []	#List with all threads


thread_counter = 0
relay_index_download = 3 # -1 by default (is 3 for testing)

random_generator = ''
private_key = ''
public_key = ''


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

select_mode()

input = ""

while input != "exit":
	input = ""
	input = raw_input("Awaiting Command: ")

	temp = []
	temp = input.split(' ')
	
	serverindex = -1
	iteration_number = ''
	command = ''
	error = 0
	
	mark = 0
	
	try:
		length = len(temp)
	
		if length == 3:
			serverindex = names.index(temp[0])
			iteration_number = temp[1]
			command = temp[2]
			
			if command != "latency" and command != "ping" and command != "traceroute" and command != "trace":
				raise ValueError("")
			
		elif length == 2:
			if temp[1] == "traceroute" or temp[1] == "trace":
				serverindex = names.index(temp[0])
				command = temp[1]
			elif temp[1] == "download":
				serverindex = downloads.index(temp[0])
				command = temp[1]
			else:
				raise ValueError("")
		elif length == 1:
			if temp[0] == "back":
				error = 3
			elif temp[0] == "exit":
				error = 2
			else:
				error = 1
		else:
			error = 1
		
	except ValueError:
		print "Invalid input"
		pass
	except IndexError:
		print "Invalid Command Format"
		pass
	
	
	if error == 0:
	
		if mode == 'D':
	
			if (command == "ping"):
			
				iteration_number = int(iteration_number)
				thread1 = ping_Thread(1, "Thread-" + str(thread_counter), 1)
				thread_counter += 1
				thread1.setHostname(dns[serverindex])
				thread1.setIterations(iteration_number)
				thread_list.append(thread1)
				thread1.start()
				
			elif ((command == "traceroute") or (command == "trace")):
				
				thread1 = trace_Thread(1, "Thread-" + str(thread_counter), 1)
				thread_counter += 1
				thread1.setHostname(dns[serverindex])
				thread_list.append(thread1)
				thread1.start()
		
			elif (command == "latency"):
				iteration_number = int(iteration_number)
				thread1 = latency_Thread(1, "Thread-" + str(thread_counter), 1)
				thread_counter += 1
				thread1.setHostname(dns[serverindex])
				thread1.setIterations(iteration_number)
				thread_list.append(thread1)
				thread1.start()
			
			elif (command == "download"):
				thread1 = download_Thread(1, "Thread-" + str(thread_counter), 1)
				thread_counter += 1
				thread1.setHostname(downloads[serverindex])
				thread1.setExtension(extensions[serverindex])
				thread_list.append(thread1)
				thread1.start()
				
	
		if (mode == 'R'):
			
			i = 0
			for c in relays:
				
				if (command == "ping"):
				
					iteration_number = int(iteration_number)
					thread1 = relay_Thread(1, "Thread-" + str(thread_counter), 1)
					thread_counter += 1
					thread1.setHostname(dns[serverindex])
					thread1.setIterations(iteration_number)
					thread1.setIterator(i)
					thread1.setCommand(command)
					thread_list.append(thread1)
					thread1.start()
					
				elif ((command == "traceroute") or (command == "trace")):
					
					thread1 = relay_Thread(1, "Thread-" + str(thread_counter), 1)
					thread_counter += 1
					thread1.setHostname(dns[serverindex])
					thread1.setIterator(i)
					thread1.setCommand(command)
					thread_list.append(thread1)
					thread1.start()
			
				elif (command == "latency"):
					iteration_number = int(iteration_number)
					thread1 = relay_Thread(1, "Thread-" + str(thread_counter), 1)
					thread_counter += 1
					thread1.setHostname(dns[serverindex])
					thread1.setIterations(iteration_number)
					thread1.setIterator(i)
					thread1.setCommand(command)
					thread_list.append(thread1)
					thread1.start()
				
				i += 1
				
			if command == "download":
				#get_optimal_path()
				relay_index_download = 3	#CHANGE THIS
				
				thread1 = relay_download_Thread(1, "Thread-" + str(thread_counter), 1)
				thread_counter += 1
				thread1.setHostname(downloads[serverindex])
				thread1.setExtension(extensions[serverindex])
				thread1.setIterator(relay_index_download)
				thread_list.append(thread1)
				thread1.start()
				
				
			
	
	elif error == 3:
		select_mode()
		
	elif error == 2:
		sys.exit()
		
print "BAD PRINT!"		
sys.exit()
#

'''
# Start new Threads

i = 0
for i in range(2):
	thread_list[i].join()
#	
'''
#Print of my Heart
print("Hello Python you evil Wench!")
