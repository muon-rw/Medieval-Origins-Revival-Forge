data modify entity @s NoAI set value 0
fill ~-1 ~-2 ~-1 ~1 ~2 ~1 minecraft:air replace minecraft:cobweb
kill @e[type=minecraft:falling_block,nbt={BlockState:{Name:"minecraft:cobweb"}}]
tag @s remove otherworldoriginsArachnaeWebbed
particle minecraft:block minecraft:cobweb ~ ~ ~
power revoke @s otherworldorigins:arachnae/webbed_entity