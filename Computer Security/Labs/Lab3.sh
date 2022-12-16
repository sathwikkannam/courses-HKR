#!/bin/bash

# Flush iptables
iptables -F


# Configure policies
iptables --policy OUTPUT DROP
iptables --policy INPUT DROP
iptables --policy FORWARD DROP


# Accept loopback traffic
iptables -A INPUT -i lo -j ACCEPT
iptables -A OUTPUT -o lo -j ACCEPT


# DNS
iptables -A OUTPUT -p udp --dport 53 -j ACCEPT
iptables -A INPUT -p udp --sport 53 -j ACCEPT


# Allow incoming and outgoing HTTP/HTTPS traffic
iptables -A INPUT -p tcp -m multiport --sports 80,443 -m state --state RELATED,ESTABLISHED -j ACCEPT
iptables -A OUTPUT -p tcp -m multiport --dports 80,443 -m state --state NEW,ESTABLISHED -j ACCEPT


# Show rules
iptables -L -n



