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








ips = ["147.52.19.20","147.52.19.46","147.52.19.55","147.52.19.28","147.52.19.9"]
ping_list = []
ping_list1 = []
ping_list2 = []
ping_list3 = []
ping_list4 = []
pingmono = []

def ping_relays(y,ping_list,sk):
    
	global ips
	i = int(y)
	hostname = ips[i]
	#print str(sk)
	#print hostname
	filename = "pingiosOLE" + str(sk) +".txt"
	

	#and then check the response...
	response = os.system("ping -c 1 " + hostname + " >> " + filename)
	if response == 0:
	  print hostname, 'is up!'
	else:
	  print hostname, 'is down!'
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
	ping_list.append(pe[1])

for y in range(0,5):
	for i in range (0,5):
		ping_relays(y,ping_list,i+y)

print ping_list

