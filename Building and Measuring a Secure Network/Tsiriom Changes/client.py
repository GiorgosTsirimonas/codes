#Hy335b

#imports
import sys
import threading
import os
import urllib

from socket import *
from urllib2 import URLError



#

def ping_server(hostname, iterations):
	i = 0
	average_list = []
	pingmono = []
	pingresult = []
	average = []
	for i in range(0,iterations):
		response = os.system("ping -c 1 " + hostname + " >> pingios.txt")

		#and then check the response...
		if response != 0:
			print hostname, 'is down!'
			print 'server can not be reached'
			return -1
		  


		with open("pingios.txt") as zfile:
			for grammi in zfile.readlines():
				ylist = grammi.split()
				pingmono.append(ylist)

		pingresult = pingmono[-1]
		average = pingresult[3]
		print average

		average1 = average.split('/')
		average_list.append(average1[1])
		
		os.remove("pingios.txt")
	
	sum = 0
	i = 0
	for i in range(iterations):
		sum += float(average_list[i])
	sum /= iterations
	return sum

	
	
def traceroute_server(hostname):
	trace = []
	trace_results = []
	hops = []

	traceroute = os.system("traceroute -w 150 " + hostname + " >> traceroute.txt")

	with open("traceroute.txt") as mfile:
		for line in mfile.readlines():
			mylist = line.split()
			trace.append(mylist)

	trace_results = trace[-1]
	hops = trace_results[0]	
	os.remove("traceroute.txt")
	return hops
	
	
	
#

endservers_filename = ''
relaynodes_filename = ''

dns = []
names = []

thread_list = []

mode = 'N'


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
#	


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




#Main

input = ""

while input != "exit":
	input = ""
	input = raw_input("Awaiting Command: ")

	temp = ''
	temp = input.split(' ')
	
	serverindex = ''
	iteration_number = ''
	command = ''
	error = 0
	
	try:
		serverindex = names.index(temp[0])
		
		iteration_number = temp[1]
		
		command = temp[2]
		
		if (command != "latency") and (command != "ping") and (command != "traceroute"):
			raise IndexError("")
		
	except ValueError:
		if input != "exit":
			print "There is no server with this name"
			error = 1
		pass
	except IndexError:
		print "Invalid Command Format"
		error = 1
	
	iteration_number = int(iteration_number)
	#print serverindex
	#print iteration_number.isdigit()
	#print command
	if (command == "ping"):
		print ping_server(dns[serverindex], iteration_number)
	elif (command == "traceroute"):
	    print traceroute_server(dns[serverindex])
	
	

	#if mode == 'R':
		#print input
	
	#elif mode == 'D':
		#print input
	
sys.exit()




#


#Threads
class ping_Thread (threading.Thread):
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
	def run(self):
		#print "Starting " + self.name
		insult_them(self.name)
		
		
		#print "Exiting " + self.name
		

	  
class traceroute_Thread (threading.Thread):
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
	def run(self):
		#print "Starting " + self.name
		insult_them(self.name)
		#print "Exiting " + self.name
	  





class special_Thread (threading.Thread):
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
	def run(self):
		print "Starting " + self.name
		insult_them(self.name)
		print "Exiting " + self.name

	  
def insult_them(speaker):
	print "\n" + speaker + " Yo momma is so fat...\n that she alone is responsible for the famine in Africa!"
	

	
	

	

	
# Create new threads
thread1 = special_Thread(1, "Thread-1", 1)
thread_list.append(thread1)
thread2 = special_Thread(2, "Thread-2", 2)
thread_list.append(thread2)



# Start new Threads
i = 0
for i in range(2):
	thread_list[i].start()
	

i = 0
for i in range(2):
	thread_list[i].join()


#thread1.start()
#thread2.start()
#	




	


	

#TestPrints
for text in dns:
	print(text)

for text in names:
	print(text)

	
#Print of my Heart
print("Hello Python you evil Wench!")

