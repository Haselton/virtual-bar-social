extends Node3D

@onready var player = $Player
@onready var camera: Camera3D = $Player/CameraRig/Camera3D

func _unhandled_input(event: InputEvent) -> void:
    if event is InputEventScreenTouch and event.pressed:
        _tap_to_walk(event.position)
    elif event is InputEventMouseButton and event.pressed and event.button_index == MOUSE_BUTTON_LEFT:
        _tap_to_walk(event.position)

func _tap_to_walk(screen_pos: Vector2) -> void:
    var origin := camera.project_ray_origin(screen_pos)
    var end := origin + camera.project_ray_normal(screen_pos) * 100.0
    var query := PhysicsRayQueryParameters3D.create(origin, end, 1)
    var hit := get_world_3d().direct_space_state.intersect_ray(query)
    if hit:
        player.set_destination(hit.position)
