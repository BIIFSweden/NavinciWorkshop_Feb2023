# Part II - Cell segmentation and dot detection in color images.
## Overview
In this exercise we will learn how to handle colored brightfield images. Here the central part is "Color Deconvolution", where we split the colors into individual channels. 

## Image data
- BJ_bf_not_stimulated.tif
- BJ_bf_stimulated.tif

## Preparations
-	Create a new project in QuPath under `File > Project`. Choose an empty folder to create the project. You can also re-use the project from part I.
-	Add the images:
- BJ_bf_not_stimulated.tif
-	BJ_bf_stimulated.tif
While importing set the Image type to `H-DAB`.

Let's start with `BJ_bf_not_stimulated.tif`: double-click on the image to start analysis.
The image scaling seems to be wrong. In the image tab change the pixel size to the right value (0.353 µm?).

## Color Deconvolution
When importing a brightfield image, QuPath automatically separates the colors into individual channels ("color deconvolution"). Giving the information of `H-DAB` while importing the images to QuPath, we passed on the information of which colors we expect in the images. You can read more about color deconvolution in QuPath in the documentation: https://qupath.readthedocs.io/en/0.3/docs/tutorials/separating_stains.html

Let's inspect the single channels after the automatic color deconvolution: 
- Original
- Hematoxylin
- DAB
- Residual

Under `View > Brightness/Contrast` adjust each channel. Use `show grayscale` and `invert background` to get a fluorescence-like image.
With `View > Show channel viewer` you can inspect the channels in a montage view:
![](images/screenshot_channel_viewer_original.png?raw=true "Screenshot")

We now try to improve the color deconvolution. For this:
-	Draw a rectangle covering a smaller area with cells, dots and background
-	Run `Analyze > Preprocessing > Estimate stain vectors` 
-	Click `Yes` confirming the new modal RGB values as background values.  
	
![](images/screenshot_estimate_stain_vectors_bg.png?raw=true "Screenshot")
-	In the `Visual Stain Editor` we can inspect the color deconvolution. Click `Auto` to re-calculate the stain vectors according to the selected area.  
![](images/screenshot_visual_stain_editor.png?raw=true "Screenshot")
-	Click `OK` and save the settings under a new name.  
![](images/screenshot_estimate_stain_vectors.png?raw=true "Screenshot")
-	Re-inspect the single channels: Original, Hematoxylin, DAB, Residual. 
-	![](images/screenshot_channel_viewer_adjusted.png?raw=true "Screenshot")

## Cell Detection
Once the colors are well separated to different channel, we can treat the image as any multi-channel fluorescence microscopy image data.
For cell detection we can therefore proceed as for data set 1.
- Delete the annotation used for color deconvolution and draw a rectangular annotation to cover the entire image
- Run `Analyze > Cell detection`

### Exercise: 
Try to find settings for cell detections by yourself!
-	Suggested settings:  

## Subcellular Detection
We are now detecting the dots in the DAB channel. As in part I, use `Analyze > Cell detection > Subcellular detection (experimental)`.
### Exercise: 
Try to find settings for cell detections by yourself!
-	Suggested settings:
For inspection it can be nice to go back in `View > Brightness/Contrast` to the `Original`, and untick `show grayscale` and `invert background`.
It can also help to toggle on/off the annotation.
![](images/screenshot_annotation_bar.png?raw=true "Screenshot")
Final result:
![](images/screenshot_cell_wannotations.png?raw=true "Screenshot")


## Automating a Workflow
Open `Automate>Show workflow command history`.  
![](images/screenshot_workflow_viewer.png?raw=true "Screenshot")
The Workflow viewer lists the commands we have used so far and recorded the parameters we entered. You can for example click on the `Cell detection` and it will show the parameters used. Double-clicking on a command will open the command window, with the parameters pre-filled. The viewer lists the commands with the first used one on the top, the last on the bottom. Especially when trying out parameters it will list several times the same command, containing each different parameters. 

Click `Create script` in the Workflow viewer. It will now open the commands with the set parameters in the script editor. Get rid of duplicates and un-wanted commands by deleting single lines. After cleaning, the script looks like this:
![](images/screenshot_script_editor_cleaned.png?raw=true "Screenshot")

Save the script with `File > Save as...` within the Script Editor. Now let's try it out on the second image of this image set: BJ_bf_stimulated.tif
For this let's go to back to the main QuPath window and `Project` and double-click on BJ_bf_stimulated.tif to activate the image. Click `Yes` to save the changes we made in the first images.
Now:
- draw a rectangular annotation to cover the entire image
- go to the `Script Editor` and click `Run > Run`.

This is the result:
![](images/screenshot_cell_wannotation_stimulated.png?raw=true "Screenshot")

The script separates the color using the same stain vectors as in the first image, and then runs both `Cell detection `and `subcellular detection`. It is worth checking each step. Were colors well separated? Do the parameters for cell detection and subcellular detection also fit to the new image?





