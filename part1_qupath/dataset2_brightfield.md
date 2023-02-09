# Part II - Cell segmentation and dot detection in color images.
## Overview
In this exercise we will learn how to handle colored brightfield images. Here the central part is "Color Deconvolution", where we split the colors into individual channels. We will also learn how to create a workflow script to run the workflow on a different image.

## Image data
- BJ_bf_not_stimulated.tif
- BJ_bf_stimulated.tif

## Preparations
-	Create a new project in QuPath under `File > Project`. Choose an empty folder to create the project. You can also re-use the project from part I.
-	Add the images: BJ_bf_not_stimulated.tif and BJ_bf_stimulated.tif
	
While importing set the Image type to `H-DAB`.

Let's start with `BJ_bf_not_stimulated.tif`: double-click on the image to start analysis.
The image scaling seems to be wrong. In the image tab change the pixel size to the right value (0.353 µm?).

## Color Deconvolution
Colored images come with a red, green, blue intensity value for each pixel. There are different method to digitally separate the three stains. Giving the information of `H-DAB` while importing the images to QuPath, we passed on the information of which colors we expect in the images. QuPath then automatically separates the colors into individual channels ("color deconvolution") while importing. You can read more about color deconvolution in QuPath in the documentation: https://qupath.readthedocs.io/en/0.3/docs/tutorials/separating_stains.html

Under `View > Brightness/Contrast` one can see that QuPath automatically separates the channels using different methods. Hematoxylin, DAB, Residual are the 3 resulting channels from color deconvolution, giving the information that we expect colors to follow an H-DAB color scheme.  

Let's inspect the single channels after the automatic color deconvolution: 
- Hematoxylin
- DAB
- Residual

Under `View > Brightness/Contrast` adjust each channel. Use `show grayscale` and `invert background` to get a fluorescence-like image. We aim for seeing the signal of the blue nuclei in one channel, the signal of the brown/red dots in another channel.

With `View > Show channel viewer` you can inspect the channels in a montage view:
![](images/screenshot_channel_viewer_original.png?raw=true "Screenshot")

The automated color separation is quite good. We can still try to improve the color deconvolution. For this:
-	Draw a rectangle covering a smaller area with cells, dots and background
-	Run `Analyze > Preprocessing > Estimate stain vectors` 
-	Click `Yes` confirming the new modal RGB values as background values.  
	
![](images/screenshot_estimate_stain_vectors_bg.png?raw=true "Screenshot")
-	In the `Visual Stain Editor` we can inspect the color deconvolution. Click `Auto` to re-calculate the stain vectors according to the selected area.  
![](images/screenshot_visual_stain_editor.png?raw=true "Screenshot")
-	Click `OK` and save the settings under a new name.  
![](images/screenshot_estimate_stain_vectors.png?raw=true "Screenshot")
-	Re-inspect the single channels: Hematoxylin, DAB, Residual. 
-	![](images/screenshot_channel_viewer_adjusted.png?raw=true "Screenshot")

## Cell Detection
Once the colors are well separated to different channel, we can treat the image as any multi-channel fluorescence microscopy image data.
For cell detection we can therefore proceed as for data set 1.
- Delete the annotation used for color deconvolution and draw a rectangular annotation to cover the entire image
- Run `Analyze > Cell detection`

### Exercise: 
Try to find settings for cell detections by yourself!


<details>
	
  <summary>Suggested settings - click</summary>
	
![](images/screenshot_settings_cell_detection.png?raw=true "Screenshot")
	
</details>



## Subcellular Detection
We are now detecting the dots in the DAB channel. As in part I, use `Analyze > Cell detection > Subcellular detection (experimental)`.
### Exercise: 
Try to find settings for the subcellular detections by yourself!
<details>
  <summary>Suggested settings:</summary>
	
![](images/screenshot_settings_subcellular_detection.png?raw=true "Screenshot")

</details>

## Final result
After all the steps, the final results looks like this:  
![](images/screenshot_cell_wannotations.png?raw=true "Screenshot")


# Automating a Workflow
Here we will learn how to run the workflow on a new image. We start by opening the Workflow command history by choosing `Automate>Show workflow command history`.  

![](images/screenshot_workflow_viewer.png?raw=true "Screenshot")

The Workflow viewer lists the commands we have used so far and recorded the parameters we entered. You can for example click on `Cell detection` and it will show the parameters used. Double-clicking on a command will open the gui of the command, with the parameters pre-filled. The viewer lists the commands with the one used first on the top, the last at the bottom. Especially when trying out parameters it will list several times the same command, containing each the parameters used at that time. 

Click `Create script` in the Workflow viewer. It will now open the commands with the parameters set in the script editor. Get rid of duplicates and un-wanted commands by deleting single lines. After cleaning, the script looks like this:
![](images/screenshot_script_editor_cleaned.png?raw=true "Screenshot")

Save the script with `File > Save as...` within the Script Editor. Now let's try it out on the second image of this image set: BJ_bf_stimulated.tif
For this let's go to back to the main QuPath window to the tab `Project` and double-click on BJ_bf_stimulated.tif to activate the image. Click `Yes` to save the changes we made in the first image.
Now:
- draw a rectangular annotation to cover the entire image
- go to the `Automate>Script Editor` and click `Run > Run`.

This is the result:

![](images/screenshot_cell_wannotation_stimulated.png?raw=true "Screenshot")

The script separates the color using the same stain vectors as in the first image, and then runs both `Cell detection `and `Subcellular detection`. It is worth checking each step. Were colors well separated? Do the parameters for cell detection and subcellular detection also fit to the new image?
When toggling on/off the annotations we observe that the threshold for the subcellular detection was too low for this image: particles of the background are detected as spots as a result. When automating a workflow it is important that the settings fit for all the images, similarily as you would keep your incubation and microscopy settings the same for all samples. Try to find settings that fit both images.  

Note that after running the script you can:
- Open `Automate>Show workflow command history`
- double-click on `Subcellular spot detection` in the `Workflow viewer`. We now see the parameters we had used via the script. 
- Tune the parameters starting from the previously set ones.







