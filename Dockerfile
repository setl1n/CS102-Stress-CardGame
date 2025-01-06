FROM ubuntu:22.04

# Prevent timezone prompt during installation
ENV DEBIAN_FRONTEND=noninteractive

# Install required packages with additional audio support
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    xvfb \
    x11vnc \
    novnc \
    websockify \
    pulseaudio \
    alsa-utils \
    libasound2 \
    libasound2-plugins \
    pulseaudio-utils \
    && rm -rf /var/lib/apt/lists/*

# Configure PulseAudio
RUN mkdir -p /var/run/pulse /var/lib/pulse /root/.config/pulse
RUN touch /var/run/pulse/.keep /var/lib/pulse/.keep

# Create pulse config
COPY pulse-client.conf /etc/pulse/client.conf
RUN echo "default-server = unix:/tmp/pulse/native" >> /etc/pulse/client.conf
RUN echo "autospawn = no" >> /etc/pulse/client.conf
RUN echo "daemon-binary = /bin/true" >> /etc/pulse/client.conf
RUN echo "enable-shm = false" >> /etc/pulse/client.conf

# Configure virtual sound device
RUN echo "pcm.!default { type plug slave.pcm \"null\" }" >> /etc/asound.conf
RUN echo "ctl.!default { type hw card 0 }" >> /etc/asound.conf

# Create pulse config directory
RUN mkdir -p /etc/pulse
RUN echo "default-sample-rate = 44100" >> /etc/pulse/daemon.conf

# Create directory for the application
WORKDIR /app

# Copy the application files
COPY classes/ ./classes/
COPY start.sh ./

# Make the startup script executable
RUN chmod +x start.sh

# Expose VNC port
EXPOSE 6080

# Set environment variable for display
ENV DISPLAY=:99

# Add audio environment variables
ENV PULSE_SERVER=unix:/tmp/pulse/native
ENV PULSE_COOKIE=/tmp/pulse/cookie

# Start the application
CMD ["./start.sh"]
