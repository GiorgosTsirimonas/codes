import os
import sys
import threading
import urllib
import requests
import urllib
import time
import random, string

from sys import argv
from socket import *
from urllib2 import URLError
from Crypto.Cipher import DES
from threading import Thread
from time import sleep

  # seconds since the UNIX epoch

# upload your file



min = 10000.00000
min_relay = -1
ips = ["147.52.19.20","147.52.19.46","147.52.19.55","147.52.19.28","147.52.19.9"]
hoping_for_the_worst = []
trace = []
trace_results = []
hops = []
relayLock = threading.Lock()

class ping_realys_thread (threading.Thread):
	
	def __init__(self, threadID, name, counter):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
		self.hostname = ''
		self.iterations = ''
		self.ping_list = ''
		self.sk = ''
	def setHostname(self, hostname):
		self.hostname = hostname
	def setIterations(self, iterations):
		self.iterations = iterations
	def setPing_list(self,ping_list):
		self.ping_list = ping_list
	def setsk(self,sk):
		self.sk = sk
	def run(self):
		result = ping_relays(self.hostname,self.ping_list,self.sk)
		#relayLock.acquire(1)
		if (result != None):
			self.ping_list.append(result)
		#relayLock.release()
		#ping_relays(self.hostname,self.ping_list,self.sk)
		#print "\n" + self.name + " " + self.hostname + " :: RTT: " + str(ping_server(self.hostname, self.iterations))
		


def troll(p):
	i = int(p)
	print "kalws hr8es sto masini "
	filename = "tracios" + str(i) +".txt"
	hostname = ips[i]
	traceroute = os.system("traceroute -w 150 " + hostname + " >> " + filename)
	print "egine t trace"
	with open(filename) as mfile:
		for line in mfile.readlines():
			mylist = line.split()
			trace.append(mylist)

	trace_results = trace[-1]
	hops = trace_results[0]
	print "Direct: Number of hops to " + hostname + " = " + hops
	
	a = int(hops)
	hoping_for_the_worst.append(a)



def ping_relays(y,ping_list,sk):
    
	global ips
	i = int(y)
	hostname = ips[i]
	pingmono = []
	pingresult = []   
	average = []
	#print str(sk)
	#print hostname
	filename = "pingiosOLE" + str(sk) +".txt"
	

	#and then check the response...
	response = os.system("ping -c 1 " + hostname + " >> " + filename)
	if response == 0:
	  print hostname, 'is up!'
	else:
	  print hostname, 'is down!'
	  os.remove(filename)
	  return
	  


	with open(filename) as zfile:
		for grammi in zfile.readlines():
			ylist = grammi.split()
			pingmono.append(ylist)

	pingresult = pingmono[-1]
	average = pingresult[3]

	pe = average.split('/')
	#pe = pe
	'''
	if (i == 0):
		ping_list.append(pe[1])
	if (i == 1):
		ping_list1.append(pe[1])
	if (i == 2):
		ping_list2.append(pe[1])
	if (i == 3):
		ping_list3.append(pe[1])
	if (i == 4):
		ping_list4.append(pe[1])
	'''
	#ping_list.append(pe[1])
	os.remove(filename)
	#print str(pe[1])
	return pe[1]

def ping_time():
	global ips
	global min
	global min_relay
	ping_list = []
	ping_list1 = []
	ping_list2 = []
	ping_list3 = []
	ping_list4 = []
	pingrelaysthreads = []
	sk = 0
	for i in range (0,5):
		for ty in range(0,5):
			t = ping_realys_thread(1,"relay_ping_thread"+str(sk),1)
			t.setHostname(i)
			if (ty == 0):
				t.setPing_list(ping_list)
			elif (ty == 1):
				t.setPing_list(ping_list1)
			elif (ty == 2):
				t.setPing_list(ping_list2)
			elif (ty == 3):
				t.setPing_list(ping_list3)
			elif (ty == 4):
				t.setPing_list(ping_list4)
			t.setsk(sk)
			sk = sk + 1
			pingrelaysthreads.append(t)
			t.start()
			t.join()
	for i in range (0,len(pingrelaysthreads)):
		t = pingrelaysthreads[i]
		t.join()
	print ping_list
	print ping_list1
	print ping_list2
	print ping_list3
	print ping_list4
	new = []
	sum = 0
	mpe = len(ping_list)
	for z in range(0,mpe):
			sum += float(ping_list[z])
	if (sum != 0):
		sum /= mpe
	new.append(sum)

	sum = 0
	mpe = len(ping_list1)
	for z in range(0,mpe):
			sum += float(ping_list1[z])
	if (sum != 0):
		sum /= mpe
	new.append(sum)

	sum = 0
	mpe = len(ping_list2)
	for z in range(0,mpe):
			sum += float(ping_list2[z])
	if (sum != 0):
		sum /= mpe
	new.append(sum)

	sum = 0
	mpe = len(ping_list3)
	for z in range(0,mpe):
			sum += float(ping_list3[z])
	if (sum != 0):
		sum /= mpe
	new.append(sum)

	sum = 0
	mpe = len(ping_list4)
	for z in range(0,mpe):
			sum += float(ping_list4[z])
	if (sum != 0):
		sum /= mpe
	new.append(sum)

	print new
	mpe = len(new)

	
	
	for z in range(0,mpe):
		if (min > new[z] and new[z] != 0):
			min = new[z]
			min_relay = z
			##min_relay1 = -1
		##elif (min == new[z]):
		##	min_relay1 = z
	print "as doume ton kalytero xrono"
	print min
	
	
ping_time()
print "MPEEEEE"
'''
for i in range (0,5):
    for ty in range(0,5):
		t = Thread(target=ping_relays, args=(ty,))
		t.start()
		sleep(1)
	
sleep(5)'''
'''
print "hr8e h wra gia t ping sas"
print ping_list
print ping_list1
print ping_list2
print ping_list3
print ping_list4

new = []
sum = 0
mpe = len(ping_list)
for z in range(0,mpe):
		sum += float(ping_list[z])
if (sum != 0):
	sum /= mpe
new.append(sum)

sum = 0
mpe = len(ping_list1)
for z in range(0,mpe):
		sum += float(ping_list1[z])
if (sum != 0):
	sum /= mpe
new.append(sum)

sum = 0
mpe = len(ping_list2)
for z in range(0,mpe):
		sum += float(ping_list2[z])
if (sum != 0):
	sum /= mpe
new.append(sum)

sum = 0
mpe = len(ping_list3)
for z in range(0,mpe):
		sum += float(ping_list3[z])
if (sum != 0):
	sum /= mpe
new.append(sum)

sum = 0
mpe = len(ping_list4)
for z in range(0,mpe):
		sum += float(ping_list4[z])
if (sum != 0):
	sum /= mpe
new.append(sum)

print new
mpe = len(new)


for z in range(0,mpe):
	if (min > new[z] and new[z] != 0):
		min = new[z]
		min_relay = z
		min_relay1 = -1
	elif (min == new[z]):
		min_relay1 = z
print "as doume ton kalytero xrono"
print min
print min_relay
'''
'''
min_relay1 = 3
min_relay = 2
if (min_relay1 != -1):
	
	thredios = Thread(target=troll, args=(min_relay,))
	thredios.start()
	thredios.join()
	##thredios = Thread(target=troll, args=(min_relay1,))
	##thredios.start()
	
	
	
	
print hoping_for_the_worst
if (hoping_for_the_worst == []):
	print "they have the same hops,we are choosing the first"
'''
	
'''





print ips

hostname = "google.com" #example
i=0
for i in range(0,2):
	response = os.system("ping -c 1 " + hostname + " >> pingios.txt")

	#and then check the response...
	if response == 0:
	  print hostname, 'is up!'
	else:
	  print hostname, 'is down!'
	  


	with open("pingios.txt") as zfile:
		for grammi in zfile.readlines():
			ylist = grammi.split()
			pingmono.append(ylist)

	pingresult = pingmono[-1]
	average = pingresult[3]
	print average

	peos = average.split('/')
	arxidi.append(peos[1])
	
	os.remove("pingios.txt")
	print arxidi 
'''











'''

x = ''.join(random.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for _ in range(16))
print(x)

elapsed = -1
req = 'abc'
start = time.time()
try:
	urllib.urlretrieve(req,"file")
	elapsed = time.time() - start
except IOError:
	print ("marko pipolo is here")
	pass



print elapsed

arxikh = []

des = DES.new('01234567', DES.MODE_ECB)
text = 'abcdefgh'
cipher_text = des.encrypt(text)
print cipher_text
t = des.decrypt(cipher_text)
print t


with open("file") as zfile:
		for grammi in zfile.readlines():
			ylist = grammi.split()
			arxikh.append(ylist)
print arxikh





pingmono = []
pingresult = []   https://docs.python.org/3/howto/urllib2.html
average = []
arxidi = []

hostname = "google.com" #example
i=0
for i in range(0,2):
	response = os.system("ping -c 1 " + hostname + " >> pingios.txt")

	#and then check the response...
	if response == 0:
	  print hostname, 'is up!'
	else:
	  print hostname, 'is down!'
	  


	with open("pingios.txt") as zfile:
		for grammi in zfile.readlines():
			ylist = grammi.split()
			pingmono.append(ylist)

	pingresult = pingmono[-1]
	average = pingresult[3]
	print average

	peos = average.split('/')
	arxidi.append(peos[1])
	
	os.remove("pingios.txt")
print arxidi 




ping_results = pings[-1]
			avg_ping = ping_results[3].split('/')
			avg_ping = avg_ping[1]
			
			
			

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
print "Direct: Number of hops to " + hostname + " = " + hops'''

##os.remove("traceroute.txt")