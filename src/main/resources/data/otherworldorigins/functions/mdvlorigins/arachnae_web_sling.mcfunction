tag @e[type=!minecraft:player,type=!#otherworldorigins:bosses,distance=..12,limit=8,sort=nearest] add otherworldoriginsArachnaeWebbed 
execute at @e[type=#otherworldorigins:bosses,type=!minecraft:player,distance=..12] run particle minecraft:block minecraft:cobweb ~ ~ ~
execute at @e[tag=otherworldoriginsArachnaeWebbed,distance=..12] run summon minecraft:leash_knot ~ ~ ~ {Tags:["WebCentre"]}
execute as @e[tag=WebCentre,distance=..12] run tp @e[tag=otherworldoriginsArachnaeWebbed,limit=1,sort=nearest,distance=..12] @s
execute as @e[tag=otherworldoriginsArachnaeWebbed,distance=..12] at @s run tp @s ~ ~-0.25 ~
kill @e[tag=WebCentre]
execute as @e[tag=otherworldoriginsArachnaeWebbed,distance=..12] run power grant @s otherworldorigins:arachnae/webbed_entity
execute as @e[tag=otherworldoriginsArachnaeWebbed,distance=..12] run data modify entity @s NoAI set value 1
execute as @e[tag=otherworldoriginsArachnaeWebbed,distance=..12] at @s anchored eyes positioned ^ ^ ^ run summon minecraft:falling_block ~ ~ ~ {BlockState:{Name:"minecraft:cobweb"},Time:1b,DropItem:0}
effect give @e[tag=otherworldoriginsArachnaeWebbed,distance=..12] minecraft:slowness 10 2