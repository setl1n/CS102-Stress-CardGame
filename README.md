Work in progress branch for Java card game containerized with remote display capabilities.

## Overview
- Compiles Java game and packages it in a Docker container
- Uses X11 (virtual display server) to run graphical applications headlessly
- noVNC provides browser-based VNC client to access the virtual display
- Container hosts game through VNC, accessible via web browser

## Technical Stack
- **Docker**: Containers that package the game with all dependencies for consistent deployment
- **X11**: Unix windowing system that provides virtual display capabilities
- **noVNC**: HTML5 VNC client that enables browser access to VNC servers

## Quick Start
1. Compile the game:
   ```bash
   bash compile.sh  # or compile.bat on Windows
   ```

2. Build and start container:
   ```bash
   docker compose up
   ```

3. Access game:
   - Open browser to `localhost:6080/vnc.html`
   - Scroll to find virtual display