# Virtual Bar 3D — Production Reference

## Camera
Portrait 9:16. Third-person camera behind and above player. Player occupies lower center ~20% of screen height. Camera looks down the central circulation lane so destinations remain easy to tap.

## Environment zones
1. Long illuminated bar along left wall with stools.
2. Central open circulation/social floor.
3. Lounge seating + fireplace on right.
4. Pool table foreground-left.
5. Rear social/dance area with purple accent lighting.
6. Plants, tables and decor used as navigation obstacles.

## Materials / lighting
Dark wood, charcoal metal, leather, stone/tile floor, glass and brass. Warm 2700–3200K practical lights around bar/lounge contrasted by restrained violet accent lighting toward rear. Bake/static-light where practical for Android.

## Character target
High-quality stylized realism, not photoreal MetaHuman density. Shared humanoid skeleton. Modular hair/clothes/accessories. Base avatar approximately 25k–45k triangles at LOD0; additional LODs for room populations.

## Animation set v1
Idle breathing, walk cycle, turn/stop transition. Root-motion appearance but navigation controlled by Godot. Later: sit, talk gestures, wave, dance, drink.

## Mobile optimization
Use texture atlases where possible. Prefer 1K textures for ordinary props/characters and selective 2K hero assets. LOD distant avatars. Avoid expensive transparent materials and excessive realtime lights.

## Interaction
Single tap on navigable floor creates a destination marker. NavigationAgent3D computes route. Character accelerates/decelerates rather than snapping speed, rotates smoothly into movement, walks, then blends to idle on arrival.
