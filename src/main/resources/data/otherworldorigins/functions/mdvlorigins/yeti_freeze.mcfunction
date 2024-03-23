tag @e[type=!minecraft:player,type=!#otherworldorigins:non_freezables,distance=..12,limit=8,sort=nearest] add otherworldoriginsYetiFrozen 
execute at @e[type=#otherworldorigins:non_freezables,distance=..12] run playsound minecraft:block.lava.extinguish ambient @a ~ ~ ~ 0.25
execute at @e[tag=otherworldoriginsYetiFrozen,distance=..12] run summon minecraft:leash_knot ~ ~ ~ {Tags:["FreezeCentre"]}
execute as @e[tag=FreezeCentre,distance=..12] run tp @e[tag=otherworldoriginsYetiFrozen,limit=1,sort=nearest,distance=..12] @s
execute as @e[tag=otherworldoriginsYetiFrozen,distance=..12] at @s run tp @s ~ ~-0.25 ~
kill @e[tag=FreezeCentre]
execute as @e[tag=otherworldoriginsYetiFrozen,distance=..12] run power grant @s otherworldorigins:yeti/frozen_entity
execute as @e[tag=otherworldoriginsYetiFrozen,distance=..12] run data modify entity @s NoAI set value 1
execute as @e[tag=otherworldoriginsYetiFrozen,distance=..12] at @s anchored eyes positioned ^ ^ ^ run summon minecraft:falling_block ~ ~ ~ {BlockState:{Name:"minecraft:blue_ice"},Time:1b,DropItem:0}
execute as @e[tag=otherworldoriginsYetiFrozen,distance=..12] at @s anchored eyes positioned ^ ^ ^ run summon minecraft:falling_block ~ ~1 ~ {BlockState:{Name:"minecraft:blue_ice"},Time:1b,DropItem:0}
execute as @e[tag=otherworldoriginsYetiFrozen,distance=..12] at @s anchored eyes positioned ^ ^ ^ run summon minecraft:falling_block ~ ~2 ~ {BlockState:{Name:"minecraft:blue_ice"},Time:1b,DropItem:0}
effect give @e[tag=otherworldoriginsYetiFrozen,distance=..12] minecraft:slowness 10 2