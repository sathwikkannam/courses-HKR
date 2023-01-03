#!/bin/bash

# Flush iptables
iptables -F


# Configure policies to DROP all traffic
iptables --policy OUTPUT DROP
iptables --policy INPUT DROP
iptables --policy FORWARD DROP


# Accept DNS, Should be the first rule in the tables.
iptables -A OUTPUT -p udp --dport 53 -m state --state NEW,ESTABLISHED -j ACCEPT
iptables -A INPUT -p udp --sport 53 -m state --state ESTABLISHED -j ACCEPT


# Accept loopback traffic for local DNS servers. Not necessary for non-debian distros
iptables -A INPUT -i lo -j ACCEPT
iptables -A OUTPUT -o lo -j ACCEPT


# Allow incoming and outgoing HTTP/HTTPS traffic
iptables -A INPUT -p tcp -m multiport --sports 80,443 -m state --state ESTABLISHED -j ACCEPT
iptables -A OUTPUT -p tcp -m multiport --dports 80,443 -m state --state NEW,ESTABLISHED -j ACCEPT


# Show rules
iptables -L -n

# Save iptables configuration
iptables-save > lab3.conf

# Display configuration file
cat lab3.conf

# Restore a configuration
iptables-restore < lab3.conf