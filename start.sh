#!/bin/bash

# Clean up any existing locks and sockets
rm -rf /tmp/.X* /tmp/.x11vnc
rm -rf /run/pulse /run/user/*
rm -rf /dev/shm/pulse-shm-*

# Set up PulseAudio directories
mkdir -p /run/user/0
chown -R root:root /run/user/0
export XDG_RUNTIME_DIR=/run/user/0
mkdir -p $XDG_RUNTIME_DIR/pulse
chmod -R 700 /run/user/0

# Start PulseAudio
pulseaudio -D --system --disallow-exit --disallow-module-loading --exit-idle-time=-1 --log-target=stderr

# Create and configure Xauthority
export XAUTHORITY=/root/.Xauthority
touch $XAUTHORITY
xauth generate :99 . trusted

# Start Xvfb with better compatibility options
Xvfb :99 -screen 0 1920x1080x24 -ac +extension GLX +extension RANDR +extension RENDER -nolisten tcp &

# Wait for Xvfb
until xdpyinfo -display :99 >/dev/null 2>&1; do
    echo "Waiting for Xvfb..."
    sleep 1
done

# Start window manager
metacity --display=:99 --sm-disable --replace &

# Configure X11 permissions
xhost +local:root

# Start x11vnc with optimized settings and wait for initialization
x11vnc -display :99 \
    -forever \
    -nopw \
    -shared \
    -noscr \
    -noxdamage \
    -noxrecord \
    -noxfixes \
    -nowf \
    -skip_dups \
    -dinasty \
    -nocursorshape \
    -nocursor \
    -nobell \
    -rfbport 5900 \
    -bg \
    -o /var/log/x11vnc.log &

# Wait for x11vnc to bind to port 5900
until nc -z localhost 5900; do
    echo "Waiting for x11vnc to bind to port 5900..."
    sleep 1
done

# Load PulseAudio modules
pactl load-module module-null-sink sink_name=DummyOutput
pactl load-module module-native-protocol-unix auth-anonymous=1
pactl load-module module-always-sink

# Start noVNC with specific options
/usr/share/novnc/utils/launch.sh --vnc localhost:5900 --listen 6080 &

# Start the Java application with specific options
cd /app
DISPLAY=:99 _JAVA_AWT_WM_NONREPARENTING=1 JAVA_TOOL_OPTIONS="-Dsun.java2d.xrender=false -Dawt.useSystemAAFontSettings=on" java -cp classes app.App
