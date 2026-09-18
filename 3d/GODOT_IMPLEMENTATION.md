# Godot implementation

Recommended scene tree:

Main (Node3D)
- Environment
- NavigationRegion3D
- Player (CharacterBody3D)
  - AvatarGLB
  - AnimationTree
  - NavigationAgent3D
  - Nameplate
- CameraRig
  - Camera3D
- UI (CanvasLayer)
  - DestinationMarker
  - PresenceCount
  - BottomNavigation

Tap handling:
1. Convert screen tap to Camera3D ray.
2. Raycast against floor/navigation collision layer.
3. Send hit position to NavigationAgent3D.target_position.
4. Each physics frame obtain next path position.
5. Smoothly rotate avatar toward horizontal velocity.
6. Accelerate toward walk speed.
7. AnimationTree blends Idle -> Walk from normalized speed.
8. On arrival decelerate and blend Walk -> Idle.

Do not use a virtual joystick.

Firebase comes after local 3D movement is validated. Remote clients should eventually receive compact avatar parameters plus interpolated position/rotation/presence rather than entire meshes.
