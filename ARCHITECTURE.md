# CLEAN Architecture Documentation

This project now follows CLEAN Architecture principles to ensure maintainability, testability, and separation of concerns.

## Layer Structure

### 1. Domain Layer (`domain.entities`)
**Purpose**: Contains enterprise business rules - the core entities and value objects.
- `BlockType.java` - Enum representing different block types
- `Chunk.java` - Entity representing a 16x16x16 chunk of blocks
- `Player.java` - Entity representing the player
- `World.java` - Entity representing the game world
- `PerlinNoise.java` - Utility for terrain generation

**Dependencies**: None (innermost layer)

### 2. Application Layer (`application.usecases`)
**Purpose**: Contains application-specific business rules and use case interactors.

#### World Generation Use Case
- `WorldGenerationInputBoundary.java` - Interface for world generation
- `WorldGenerationInteractor.java` - Implements terrain generation logic using Perlin noise

#### Player Movement Use Case
- `PlayerMovementInputBoundary.java` - Interface for player movement
- `PlayerMovementInteractor.java` - Handles player movement and rotation
- `PlayerMovementInputData.java` - Input data transfer object
- `PlayerMovementOutputData.java` - Output data transfer object
- `PlayerMovementOutputBoundary.java` - Output boundary interface

**Dependencies**: Only `domain.entities`

### 3. Adapter Layer (`adapters`)
**Purpose**: Converts data between use cases and external agencies (UI, framework).

#### Controllers (`adapters.controllers`)
- `ChunkLoadingController.java` - Coordinates chunk loading between use cases and rendering
- `CameraInputController.java` - Handles camera input from the framework

**Dependencies**: `domain.entities`, `application.usecases`, framework interfaces

### 4. Frameworks Layer (`frameworks.rendering`)
**Purpose**: Framework-specific code (libGDX rendering in this case).

- `RenderPresenter.java` - Interface for presenting render data
- `SceneRenderer.java` - Implements rendering using libGDX
- `ChunkMeshBuilder.java` - Builds 3D meshes from chunk data

**Dependencies**: All inner layers, libGDX framework

## Dependency Rule

The key principle: **Dependencies point inward**. Outer layers can depend on inner layers, but inner layers never depend on outer layers.

```
Frameworks/Drivers (libGDX) 
    ↓ depends on
Interface Adapters (Controllers, Presenters)
    ↓ depends on
Application Business Rules (Use Cases)
    ↓ depends on
Enterprise Business Rules (Entities)
```

## Key Architectural Benefits

1. **Independence of Frameworks**: The core logic doesn't know about libGDX. We could swap rendering engines without changing business logic.

2. **Testability**: Use cases can be tested without UI, database, or any external element.

3. **Independence of UI**: The UI can change without affecting business rules. Could switch from 3D to 2D rendering.

4. **Independence of Database**: Business rules don't know about world persistence mechanisms.

5. **Separation of Concerns**: Each layer has a clear, single responsibility.

## Data Flow Example: Chunk Loading

1. **Main.java** (Entry point) wires dependencies
2. **ChunkLoadingController** (Adapter) polls chunks from World (Entity)
3. **WorldGenerationInteractor** (Use Case) generates terrain
4. **RenderPresenter** (Framework Interface) receives generated chunk
5. **SceneRenderer** (Framework) renders the chunk using libGDX

Note: All cross-boundary communication uses interfaces (Input/Output Boundaries) following the Dependency Inversion Principle.

## Package Naming Convention

- `domain.entities` - Core business objects
- `application.usecases.<usecase>` - Application-specific business rules
- `adapters.controllers` - Input adapters
- `adapters.presenters` - Output adapters
- `frameworks.rendering` - Rendering framework specifics

## Migration Notes

Previous structure mixed frameworks with business logic:
- `Entity` package → Now `domain.entities`
- `UseCases` package → Now `application.usecases`
- `InputBoundary` → Split into `adapters.controllers` and use case boundaries
- `io.github.testlibgdx` → Business logic extracted to use cases, rendering moved to `frameworks.rendering`

This reorganization makes the codebase more maintainable and easier to test.
