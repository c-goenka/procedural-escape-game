# Procedurally Generated Escape Game - "An Icy Adventure"

A 2D tile-based procedural world generation game built in Java. Navigate through randomly generated worlds to find a key and escape through the locked door before you're trapped forever!

## Features

- **Procedural Generation**: Every world is unique, generated from customizable seeds
- **Escape Room Mechanics**: Find the key to unlock your escape route
- **Save/Load System**: Continue your adventure anytime
- **Replay Mode**: Watch your successful runs play back
- **Interactive HUD**: Real-time tile information and game status
- **Deterministic Worlds**: Same seed = same world for consistent testing

## Quick Start

### Prerequisites
- Java 8 or higher
- Princeton's algs4 library

### Setup & Run
```bash
# Download the required library (if you don't have it)
# On Mac/Linux:
curl -O https://algs4.cs.princeton.edu/code/algs4.jar
# On Windows: Download from https://algs4.cs.princeton.edu/code/algs4.jar

# Compile the project
javac -cp ".:algs4.jar" src/Core/*.java src/TileEngine/*.java

# Run the game
java -cp ".:algs4.jar" src.Core.Main
```

## How to Play

1. **Start**: Choose "New Game (N)" from the main menu
2. **Seed**: Enter a numeric seed and press 'S' to generate your world
3. **Move**: Use WASD keys to navigate
4. **Objective**: Find the golden key scattered in the world
5. **Escape**: Reach the yelloe locked door with the key to win
6. **Save**: Press ':' followed by 'Q' to save and quit

### Controls
- `N` - New Game
- `L` - Load Game
- `R` - Replay Last Save
- `WASD` - Move Player
- `:Q` - Save & Quit

## Architecture

### Core Components
- **Engine**: Main game loop and menu system
- **WorldGenerator**: Procedural world generation algorithms
- **Game**: Player mechanics and game state management
- **TileEngine**: 2D rendering system with custom tiles

### Key Algorithms
- **Room Placement**: Rejection sampling prevents overlapping rooms
- **Corridor Generation**: L-shaped hallways connect all areas
- **Wall Detection**: Smart boundary placement using 8-directional checking
- **Object Placement**: Strategic positioning of keys and exits

## Project Structure

```
src/
├── Core/                    # Game engine and logic
│   ├── Engine.java          # Main controller and menu system
│   ├── WorldGenerator.java  # Procedural world generation
│   ├── Game.java            # Player mechanics and game state
│   └── Room.java            # Room data structure
├── TileEngine/              # 2D graphics rendering
│   ├── TERenderer.java      # Rendering engine
│   └── Tileset.java         # Tile definitions
```
