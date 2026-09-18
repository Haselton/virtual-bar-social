extends CharacterBody3D

@onready var agent: NavigationAgent3D = $NavigationAgent3D
@onready var avatar: Node3D = $Avatar
var walk_speed := 3.0
var accel := 10.0
var turning := 8.0
var moving := false

func set_destination(p: Vector3) -> void:
    agent.target_position = p
    moving = true

func _physics_process(delta: float) -> void:
    if agent.is_navigation_finished():
        moving = false
        velocity.x = move_toward(velocity.x, 0.0, accel * delta)
        velocity.z = move_toward(velocity.z, 0.0, accel * delta)
        move_and_slide()
        return

    var next := agent.get_next_path_position()
    var dir := next - global_position
    dir.y = 0.0
    if dir.length() > 0.01:
        dir = dir.normalized()
        velocity.x = move_toward(velocity.x, dir.x * walk_speed, accel * delta)
        velocity.z = move_toward(velocity.z, dir.z * walk_speed, accel * delta)
        var desired := atan2(dir.x, dir.z)
        avatar.rotation.y = lerp_angle(avatar.rotation.y, desired, turning * delta)
    move_and_slide()
