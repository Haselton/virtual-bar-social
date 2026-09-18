# Virtual Bar 3D Prototype

This folder starts the Blender -> GLB -> Godot -> Android rebuild.

## Visual target
Portrait third-person social lounge with warm amber/purple lighting, open navigation lanes, floating names/presence, detailed bar/lounge furniture, and high-quality stylized-realistic adult avatars.

## Prototype 1 acceptance criteria
- One optimized 3D lounge
- One rigged humanoid avatar
- Idle + walk animation blending
- Tap floor to set destination
- Avatar rotates smoothly toward travel direction
- Navigation avoids furniture
- Third-person portrait camera follows player
- Android export
- 60 FPS target on midrange Android hardware

## Asset pipeline
Build environment and character in Blender. Export meshes/rig/animations as GLB. Import GLB into Godot. Keep source .blend files outside the APK; ship optimized GLB/textures.

See DESIGN_SPEC.md and GODOT_IMPLEMENTATION.md.
