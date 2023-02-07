# Part II - Cell segmentation and dot detection in color images.
## Overview
In this exercise we will learn how to handle colored brightfield images. Here the central part is "Color Deconvolution", where we split the colors into individual channels. 

## Image data
- 1. Brightfield - 20X - Stimulated.tif
-	2. Brightfield - 20X - Not stimulated.tif

## Preparations
-	Create a new project in QuPath under File > Project. Choose an empty folder to create the project. You can also re-use the project from part I.
-	Add the images:
-	1. Brightfield - 20X - Stimulated.tif
-	2. Brightfield - 20X - Not stimulated.tif
While importing set the Image type to H-DAB.

Let's start with 2. Brightfield - 20X - Not stimulated.tif: double-click on the image to start analysis.
The image scaling seems to be wrong. In the image tab change the pixel size to the right value (0.353 µm?).

## Color Deconvolution
When importing a brightfield image, QuPath automatically separates the colors into individual channels ("color deconvolution"). Giving the information of "H-DAB" while importing the images to QuPath, we passed on the information of which colors we expect in the images. You can read more about color deconvolution in QuPath in the documentation: https://qupath.readthedocs.io/en/0.3/docs/tutorials/separating_stains.html

Let's inspect the single channels after the automatic color deconvolution: Original, Hematoxylin, DAB, Residual. You can use “show grayscale” and “invert background” to get a fluorescence-like image. 

We now try to improve the color deconvolution. For this:
-	Draw a rectangle covering a smaller area with cells, dots and background
-	Run Analyze > Preprocessing > Estimate stain vectors and save the settings
-	Click "Yes" confirming the new modal RGB values as background values.
![](images/screenshot_visual_stain_editor.png?raw=true "Screenshot")
-	In the Visual Stain Editor we can inspect the color deconvolution. Click "Auto" to re-calculate the stain vectors according to the selected area.
![](images/screenshot_visual_stain_editor.png?raw=true "Screenshot")
-	Click OK and save the settings under a new name.
![](images/screenshot_estimate_stain_vectors.png?raw=true "Screenshot")
-	Re-inspect the single channels: Original, Hematoxylin, DAB, Residual

## Cell Detection
Once the colors are well separated to different channel, we can treat the image as any multi-channel fluorescence microscopy image data.
For cell detection we can therefore proceed as for data set 1.
- Draw a rectangular annotation to cover the image
- Run Analyze > Cell detection

### Exercise: 
Try to find settings for cell detections by yourself!
-	Suggested settings:  

## Subcellular Detection
We are now detecting the dots in the DAB channel.
### Exercise: 
Try to find settings for cell detections by yourself!
-	Suggested settings:

## Consideration of automation

