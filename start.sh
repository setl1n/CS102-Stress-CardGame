#!/bin/bash

# Clean up any existing X11 locks
rm -f /tmp/.X*-lock
rm -f /tmp/.X11-unix/X*

# Start dbus daemon
mkdir -p /var/run/dbus
dbus-daemon --system --fork

# Set up PulseAudio directories and permissions
mkdir -p /tmp/pulse /run/pulse /var/lib/pulse
chmod -R 777 /tmp/pulse /run/pulse /var/lib/pulse
pulseaudio -D --system --disallow-exit --disallow-module-loading=false --exit-idle-time=-1

# Start Xvfb and wait for it
Xvfb :99 -screen 0 1920x1080x24 -ac &
sleep 2

# Create .Xauthority
touch ~/.Xauthority
xauth generate :99 . trusted

# Start x11vnc with proper auth
x11vnc -display :99 -forever -nopw -xauth ~/.Xauthority &

# Load PulseAudio modules
pactl load-module module-null-sink sink_name=DummyOutput
pactl load-module module-native-protocol-unix auth-anonymous=1
pactl load-module module-always-sink

# Start noVNC
/usr/share/novnc/utils/launch.sh --vnc localhost:5900 --listen 6080 &

# Wait for services to be ready
sleep 2

# Start the Java application
cd /app
DISPLAY=:99 java -cp classes app.App
