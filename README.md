# RayCaster

![Application](https://raw.githubusercontent.com/tomasz-herman/RayCaster/master/Screenshot.png)

# Controls

Movement:
- **W** - forward
- **A** - left
- **S** - backward
- **D** - right
- **Q** - down
- **E** - up

Double-click on canvas hides/shows right side-bar.  
Mouse dragging rotates the camera according to mouse movement.  
Mouse scroll zooms in or out.

# Adjustable parameters

There are two options for a camera orthographic and perspective.  
Model and Light color are fully adjustable.  
Light's exponent might be set from 1 to 100.  
Ellipsoid scale, rotation and position might be changed.  
Rendering is adaptive, by default picture is rendered at 1/32 resolution, 
then 1/16, 1/8, 1/4, 1/2 and then at full resolution. 
Number of steps might be chosen from 8 to 0(render at full resolution instantly) 