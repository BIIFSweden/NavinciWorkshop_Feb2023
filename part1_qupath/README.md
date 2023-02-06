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

Go to `Menu->File->Project->Create project`. This will ask you to create a new folder for the project, where any non-image data (annotations, trained classifiers, detections, etc.) will be stored. THe folder should be empty at start, and the images are kept at their original location and only linked to the folder.

For the new project, in the `Project` tab in the left panel, click on `Add images` and select the two .lif images from Dataset 1. You can also just drag-and-drop the images onto the QuPath window. 

## Core annotation

### Displaying individual image channels

Each image in the dataset contains image data for a single tissue-micro array (TMA) core with 4 channels as described above. Open the image for the first core (OvaryTMA-T113a_A2.lif), and use the Brightness & contrast tool from the toolbar (looks like a half-moon) to make only the DAPI channel visible in the viewport:

![](images/screenshot_brightness_contrast1.png?raw=true "Screenshot")

![](images/screenshot_brightness_contrast2.png?raw=true "Screenshot")

### Selecting a region of interest

To select a regions of interest using the Square or Ellipse tool from the toolbar to draw a region of interest, like in the screenshot: It is often a good idea to select a smaller representative region while adjusting parameters since running on the full image may take a few minutes. 

![](images/screenshot_annotation1.png?raw=true "Screenshot")

![](images/screenshot_annotation2.png?raw=true "Screenshot")

## Cell segmentation using the built-in QuPath cell detection

To segment the cells in the image, switch back to the DAPI channel, and make sure the annotation you created for the core is selected. Go to `Menu->Analyze->Cell detection->Cell detection`, and try first to segment the cells with the default settings from the first screenshot below. You may change the settings (cell expansion) to include more of the markers close to cells, before pressing "Run" again to repeat the segmentation.

![](images/screenshot_cell_seg1.png?raw=true "Screenshot")

![](images/screenshot_cell_seg2.png?raw=true "Screenshot")

## Detecting markers

The TMA images have three image channels (channel 2-4) that show markers as listed above. They are sometimes seen as individual dots, and sometimes clustered. We will use the `Menu->Analyze->Cell detection->Cell detection->Subcellular detection` to detect markers. It is easier to understand how the tool works if you start to detect markers in one channel. Use the Brightness & contrast tool from the toolbar (looks like a half-moon) to make only channel 4 visible in the viewport. Zoom in to your region of interest and hoover over a signal to see its pixel intensity in the bottom right corner of the viewport. Also hover over the background, and change the `-1` to a threshold that would separate signal from background in channel 4 (see screenshot below). A value around 1000 may be good. Check the boxes to `split by intensity`, `split by shape`, and `Include clusters`. Now press `Run. 

![](images/screenshot_subcellular.png?raw=true "Screenshot")

The resulting marker detection will outline markers in bright and dark yellow, where bright yellow outlines single markers, while dark yellow outlines clusters. The number of markers included in a cluster is approximated based on the 'Expected spot size', and 'Max spot size' decides if a spot is a cluster or not.

![](images/screenshot_channel4_markers.png?raw=true "Screenshot")

## Cell classification

### Training an object classifier

According to the signature matrix (see the file `signature_matrix.png` included in the dataset), the Glioma cell type should be expressed in the Opal 650 (mutIDH1) channel. To create a classifier for this cell type, first go to the `Annotations` tab in the left panel, and remove the existing default classes. Then add two new classes, one called Glioma and the other Not-Glioma, that should indicate if a cell is positive or negative for the cell type.

![](images/screenshot_classes1.png?raw=true "Screenshot")

![](images/screenshot_classes2.png?raw=true "Screenshot")

Next step is to add annotation points for training, on top of segmented cells. First switch to the Opal 650 channel in the viewport, to see where the marker is expressed. Select the Points tool from the toolbar, and press the `Add` button to start adding a few points for cells where the marker is expressed (have higher intensity). Assign these points the class Glioma. Now repeat this step for cells where the marker is not expressed, that should have the Not-Glioma class. To better see the intensities inside the cells, you may want to toggle showing the cell boundaries without the nuclei via `Menu->View->Cell display->Cell boundaries only`.

![](images/screenshot_singleclass1.png?raw=true "Screenshot")

![](images/screenshot_singleclass2.png?raw=true "Screenshot")

The last step is to now train a new classifier. Go to `Menu->Classify->Object classification->Train object classifier`. Follow the screenshots below to select a classifier method and which features and classes that should be used. Also select to use the points you created for the training. After pressing `Live update` and inspecting the result, give the classifier the name Glioma and press `Save`. 

![](images/screenshot_singleclass3.png?raw=true "Screenshot")

![](images/screenshot_singleclass4.png?raw=true "Screenshot")

![](images/screenshot_singleclass5.png?raw=true "Screenshot")

![](images/screenshot_singleclass6.png?raw=true "Screenshot")

### Training multiple object classifiers

After training the Glioma classifier in the previous step, you should now create and train a second classifier for a different cell type. According to the signature matrix, the Oligodendrocyte cell type should be expressed in the Opal 620 (MBP) channel. Make two new classes named Oligo and Not-Oligo, and repeat the steps of creating annotation points for training for each class. Now train a classifier for the new cell type on the Opal 620 feature measurements.

Apply the new classifier on the cells. Note how this will overwrite the previous Glioma/Not-Glioma classification! To combine multiple classifiers in QuPath, go to `Menu->Classify->Object classification->Load object classifier`, then select (using `Ctrl+Left click`) both classifiers in the list and click `Apply classifier sequentially`. Each cell should now be assigned both a Glioma/Not-Glioma class and an Oligo/Not-Oligo class.

![](images/screenshot_multiclass1.png?raw=true "Screenshot")

![](images/screenshot_multiclass2.png?raw=true "Screenshot")

### Applying a trained classifier on a different image

In the `Project` tab in the left panel, open the second image (the one you did not use for training) for the project. Repeat the steps above for core annotation and cell segmentation, and then try each of the trained classifiers on the cells in this image and compare with the results you got on the first image.

### Feature normalization (optional)

TODO

## Exporting results

### As GeoJSON (with cells exported as polygonal regions)

Go to `Menu->Objects->Select->Select detections->Select cells` to first select all the cells in the open image, and then go to `Menu->File->Object data->Export as GeoJSON` and make sure you are using the options in the screenshot below to export only selected objects (i.e., the cells). Repeat this step for each image, and name the exported files 5_10_B.geojson and 7_1_E.geojson (or similar).

![](images/screenshot_export_geojson.png?raw=true "Screenshot")

### As CSV table (with cells exported as points)

Go to `Menu->Measure->Export measurements` and select which image you want to export the cells from. Make sure you are using the options in the screenshot below to export in a CSV format that for example TissUUmaps can read. Repeat this step for each image, and name the exported files 5_10_B.csv and 7_1_E.csv (or similar).

Note: if you see the warning "A selected image is open in the viewer..." displayed like in the screenshot, you can just save the project before opening the "Export measurements" dialog again.

![](images/screenshot_export_csv.png?raw=true "Screenshot")
