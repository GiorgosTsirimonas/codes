
#import Crypto
import sys
import urllib
import time

from Crypto.PublicKey import RSA
from Crypto import Random

from Crypto.Cipher import AES

import random, string


from Crypto.Signature import PKCS1_v1_5

from Crypto.Hash import SHA



#Vimata Signing
#Create private + public keys
#exchange public keys
#hash machine name with sha
#encrypt and send plaintext,signature in this format 
#Other end creates a hash of your machine name
#other end decrypts and splits the message
#other end verifies signature with hash
plaintext = "tade sou lew"


message = "3 pappia sthn limni"




random_generator = Random.new().read


private_key = RSA.generate(1024, random_generator)


if( not (private_key.can_encrypt() and private_key.can_sign() and private_key.has_private())):
	print "There were errors"
	sys.exit()
	

public_key = private_key.publickey()


h = SHA.new(message)



signer = PKCS1_v1_5.new(private_key)

signature = signer.sign(h)

plaintext = plaintext + "," +  signature

print plaintext

format = plaintext.split(',')

print format[0]


b = SHA.new(message)

verifier = PKCS1_v1_5.new(public_key)
if verifier.verify(b, signature):
	print "The signature is authentic."
else:
	print "The signature is not authentic."



#encrypted_data = public_key.encrypt( plaintext, 32)

#print "encrypted data "
#print encrypted_data

#print private_key.decrypt(encrypted_data)




'''

#DES3 OFFERS variable block size #UNCOCKBLOCK
filename = "GRNET_Logo_Transparent-e1431694322566-1.png"


key = ''.join(random.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for _ in range(16))
#print(line)


line = "ABCD"


#KEY , MODE, BLOCK_SIZE

data = ''
#key = b'a@gtJiVfpIOKhfTe'	#sixteen byte key
iv = Random.new().read(AES.block_size)

cipher = AES.new(key, AES.MODE_CFB, iv)#CFB
cipher2 = AES.new(key, AES.MODE_CFB, iv)

msg = iv + cipher.encrypt(line)

#print type(msg) is str

print "#1 " + msg
#print "\n"
msg = cipher.decrypt(msg)[16:]
print "#2 " + msg

#i = 0
#for c in line:
#	if line[i] != msg[i]:
#		print line[i] + ":" + msg[i] + " false str"
#	else:
#		i += 1


#print msg

data = ''


with open(filename, "rb") as f_obj:
	buffer = f_obj.read(1024)
	while buffer != '':
		data += buffer
		buffer = f_obj.read(1024)


		
#print type(data) is str	
		
#print "#3 " + data
encrypted_data = iv + cipher.encrypt( data)	#edw ???

#with open ("encrypted.png", "wb") as f_obj:
	#f_obj.write(data)	

#############



decrypted_data = cipher2.decrypt(encrypted_data)[16:] #EDW

#print "#4 " + encrypted_data
#print "#5 " + decrypted_data
i = 0
for c in data:
	if data[i] != decrypted_data[i]:
		print data[i] + ":" + decrypted_data[i] + " false at " + str(i)
		break
	else:
		i += 1
		

with open ("decrypted.png", "wb") as f_obj:
	f_obj.write(decrypted_data)
		

'''






'''
name = "Hello my friend"
names = []

names = name.split(' ')

print len(names)
print names
'''

'''
downloads_filename = "files2download.txt"

downloads = []
filenames = []

with open(downloads_filename) as f_obj:
	for line in f_obj:
		text = ''
		filename = ''
		i = 0
		while (line[i] != '\r' and line[i] != '\n'):
			if line[i] == '/':
				filename = ''
			else:
				filename += line[i]
			text += line[i]
			i += 1
		downloads.append(text)
		filenames.append(filename)
		
index = 5
req = downloads[index]
start = time.time()
urllib.urlretrieve(req, filenames[index])
elapsed = time.time() - start
print elapsed
		
#print downloads
print filenames

'''




'''
plaintext = "Twra irthe i wra - tha kanoume DDOS ton server tis elhnikhs diastimikhs yphresias stis 14:00"


random_generator = Random.new().read


private_key = RSA.generate(1024, random_generator)

print private_key

if( not (private_key.can_encrypt() and private_key.can_sign() and private_key.has_private())):
	print "There were errors"
	sys.exit()
	

public_key = private_key.publickey()
encrypted_data = public_key.encrypt( plaintext, 32)

print "encrypted data "
print encrypted_data

print private_key.decrypt(encrypted_data)

'''

