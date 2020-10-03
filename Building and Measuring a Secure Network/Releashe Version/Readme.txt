
Client:
	python client.py -e end_servers.txt -r relay_nodes.txt	#Call to start client
		selectMode:
			D	#Enters Direct Mode
			R	#Enters Relay Mode
				D:
					back #goes back to mode selection
					exit #exits the script gracefully
					<end_server_name> <number of pings> ping	#ping an end server. Example: 'google 10 ping'
					<end_server_name> trace	#Run a traceroute to an end server. Example: 'youtube trace'
					<end_server_name> traceroute	#Run a traceroute to an end server. Example: 'youtube traceroute'
					<end_server_name> <number of pings> latency	#Run a combination of pings and traceroute to an end server. Example: 'youtube 5 latency'
					<file_url> download	#Downloads the requested file. Example: 'http://www.mit.edu/~gil/images/mit_logo.gif download'
				R:
					back #goes back to mode selection
					exit #exits the script gracefully
					<end_server_name> <number of pings> ping	#Send a signal to all relays to ping an end server. Example: 'google 10 ping'
					<end_server_name> trace	#Send a signal to all relays to Run a traceroute to an end server. Example: 'youtube trace'
					<end_server_name> traceroute	#Send a signal to all relays to Run a traceroute to an end server. Example: 'youtube traceroute'
					<end_server_name> <number of pings> latency	#Send a signal to all relays to Run a combination of pings and traceroute to an end server and run pings to all relays. Example: 'youtube 5 latency'
					relays <number of pings> latency	#Ping all relays. Example: 'relays 5 latency'
					<file_url> <relay_name> download	#Send a signal to a relay to download and transmit to you the requested file. Example: 'http://www.mit.edu/~gil/images/mit_logo.gif frapa download'
Relay:
	python relay_node.py -e end_servers.txt -r relay_nodes.txt	#Call to start relay
	exit	#exit the script gracefully
