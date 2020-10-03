import os
import sys
import threading
import urllib

from sys import argv
from socket import *
from urllib2 import URLError


hostname = "google.com" #example
i=0
response = os.system("ping -c 1 " + hostname + " >> pingios.txt")

#and then check the response...
if response == 0:
  print hostname, 'is up!'
else:
  print hostname, 'is down!'
  
pingmono = []
pingresult = []
average = []

with open("pingios.txt") as zfile:
	for grammi in zfile.readlines():
		ylist = grammi.split()
		pingmono.append(ylist)

pingresult = pingmono[-1]
average = pingresult[3]
print average

peos = average.split('/')
arxidi = peos[1]
print arxidi



'''


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
print "Direct: Number of hops to " + hostname + " = " + hops

##os.remove("traceroute.txt")'''