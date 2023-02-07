# QuPath instructions for workshop; Navinci Feb 8-9, 2023

By Fredrik Nysjö, Carolina Wählby and Anna Klemm

## Dataset 1, TMAs imaged by fluorescence microscopy

Folder: distributed by Agata prior to workshop

Images: 
-  OvaryTMA-T113a_A2.lif, showing ovary tissue with clear cell carcinoma (malignant)
-  OvaryTMA-T113a_A3.lif, showing ovary tissue with serous adenocarcinoma (malignant)

Markers:
-	Channel 1: Dapi – Blue – Nuclei
-	Channel 2: Fitc – Green – CA125
-	Channel 3: TxR – Red – mesothelin
-	Channel 4: FarRed – Yellow – interaction

## Goal of the analysis

With this exercise, we aim to define a regions of interest, find all cells and marker signals within this region, and extract the number of signals of each kind per cell.

## Creating a new QuPath project

Go to `Menu->File->Project->Create project`. This will ask you to create a new folder for the project, where any non-image data (annotations, trained classifiers, detections, etc.) will be stored. The folder should be empty at start, and the images are kept at their original location and only linked to the folder.

For the new project, in the `Project` tab in the left panel, click on `Add images` and select the two .lif images from Dataset 1. You can also just drag-and-drop the images onto the QuPath window. 

## Core annotation

Each image in the dataset contains image data for a single tissue-micro array (TMA) core with 4 channels as described above. Open the image for the first core (OvaryTMA-T113a_A2.lif) by doble-clinking on it. Set the image type to `Fluorescence'. Try to zoom in and out using the mouse or touch-pad. 

![](images/screenshot_set_type_to_fluorescence.png?raw=true "Screenshot")

### Selecting a region of interest

To select a regions of interest use the Square or Ellipse tool from the toolbar to draw a region of interest, like in the screenshot: 

![](images/screenshot_annotation1.png?raw=true "Screenshot")

It is often a good idea to select a smaller representative region while adjusting parameters since running on the full image may take a few minutes.

![](images/screenshot_selectROI.png?raw=true "Screenshot")

### Displaying individual image channels

Seeing all image channels at the same time may hide important information. Select the Brightness & contrast tool from the toolbar (looks like a half-moon, see below) to make only the DAPI channel visible in the viewport:

![](images/screenshot_brightness_contrast1.png?raw=true "Screenshot")

![](screenshot_set_to_gray.png?raw=true "Screenshot")


## Cell segmentation using the built-in QuPath cell detection

To segment the cells in the image, show the DAPI channel, and make sure the annotation you created for the core is selected by clicking on it. Go to `Menu->Analyze->Cell detection->Cell detection`, and try first to segment the cells with the default settings. You may change the settings for cell expansion to include more of the markers close to cells (see below), before pressing "Run" again to repeat the segmentation.

![](images/screenshot_cell_detection.png?raw=true "Screenshot")

## Detecting markers

The TMA images have three image channels (channel 2-4) that show markers as listed above. They are sometimes seen as individual dots, and sometimes clustered. We will use the `Menu->Analyze->Cell detection->Cell detection->Subcellular detection` to detect markers. It is easier to understand how the tool works if you start to detect markers in one channel. Use the Brightness & contrast tool from the toolbar (looks like a half-moon) to make only channel 4 visible in the viewport. Zoom in to your region of interest and hoover over a signal to see its pixel intensity in the bottom right corner of the viewport. Also hover over the background, and change the `-1` to a threshold that would separate signal from background in channel 4 (see screenshot below). A value around 1000 may be good. Check the boxes to `Split by intensity`, `Split by shape`, and `Include clusters`. Now press `Run.

![](images/screenshot_channel4_markers_step1.png?raw=true "Screenshot")

The resulting marker detection will outline markers in bright and dark yellow, where bright yellow outlines single markers, while dark yellow outlines clusters. The number of markers included in a cluster is approximated based on the 'Expected spot size', and 'Max spot size' decides if a spot is a cluster or not.

![](images/screenshot_channel4_markers.png?raw=true "Screenshot")

### Show measurements
To show some summary statistics of markers per cell, click on the `Show measurement table` (table icon), and select `Show detection measurements`. The table will show information regarding each cell and marker within your region of interest. To summarize the results, click on `Show histograms` at the bottom left corner of the `Detection results` panel (see below), and select `Subcellular: Channel 4: Num spots estimated`. In the screenshot below, `Count` is the number of cells, `Missing` is the number of cells that lack markers in Channel 4, and the rest of the measurements present the mean, standard deviation, minimum and maximum number of estimated spots per cell. The measurements may be saved (see `Exporting results` section) as a csv file that can be opened in e.g. Excel.

![](images/screenshot_statistics.png?raw=true "Screenshot")

Now, go back to `Menu->Analyze->Cell detection->Cell detection->Subcellular detection` and detect markers in the other channels. Note that the background fluorescence is much higher in these channels, and a suitable threshold for channels 2 and 3 is 10000, to detect red and green markers.

## Create an object classifier

If you want to classify cells as containing one or multiple markers, you can create a simple classifier. Go to the `Classify->Object classification->Create single measurement classifier` tab in the left panel, and remove the existing default classes. Then add three new classes, one called CA125_pos, mesothelin_pos and interaction_pos, that should indicate if a cell is positive or negative for a given marker. Make selections as in screenshot for the `Create single measurement classifier`:

![](images/screenshot_channel2_classifier.png?raw=true "Screenshot")


Now repeat this step for all markers. To better see the intensities inside the cells, you may want to toggle showing the cell boundaries without the nuclei via `Menu->View->Cell display->Cell boundaries only`. Create a composite classifier to find triple-positive cells using `Classify->Object classification->Create composite classifier´.

To hide or show detections and classification results, you can right click on the Annotation panel and select `Populate from existing objects->All classes (including subclasses)`. Next, use the `Show/hide...` option to show or hide the classes that you want to see.

![](images/screenshot_trippel_pos.png?raw=true "Screenshot")


### Applying a trained classifier on a different image

In the `Project` tab in the left panel, open the second image (OvaryTMA-T113a_A3.lif) for the project. Repeat the steps above for core annotation, cell segmentation and marker detection, and then try each of the trained classifiers on the cells in this image and compare with the results you got on the first image.

## Exporting results

### As GeoJSON (with cells exported as polygonal regions)

Go to `Menu->Objects->Select->Select detections->Select cells` to first select all the cells in the open image, and then go to `Menu->File->Object data->Export as GeoJSON` and make sure you are using the options in the screenshot below to export only selected objects (i.e., the cells). Repeat this step for each image, and name the exported files A2.geojson and A3.geojson (or similar). The resulting cell outlines may later be opened in e.g. TissUUmaps.

### As CSV table (with cells exported as points)

Go to `Menu->Measure->Export measurements` and select which image you want to export the measrements from. Make sure you are using the CSV format that for example TissUUmaps can read. Repeat this step for each image, and name the exported files A2.csv and A3.csv (or similar).

Note: if you see the warning "A selected image is open in the viewer...", you can just save the project before opening the "Export measurements" dialog again.


