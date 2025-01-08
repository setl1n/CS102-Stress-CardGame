#!/bin/bash

# Clean up any existing X11 locks
rm -f /tmp/.X*-lock
rm -f /tmp/.X11-unix/X*

# Start dbus daemon
mkdir -p /var/run/dbus
dbus-daemon --system --fork

# Start Xvfb and wait for it
Xvfb :99 -screen 0 3000x2000x24 -ac &
sleep 2

# Create .Xauthority
touch ~/.Xauthority
xauth generate :99 . trusted

# Start x11vnc with proper auth
x11vnc -display :99 -forever -nopw -xauth ~/.Xauthority &

# Start noVNC
/usr/share/novnc/utils/launch.sh --vnc localhost:5900 --listen 6080 &

# Wait for services to be ready
sleep 2

# Start the Java application
cd /app
DISPLAY=:99 java -cp classes app.App
