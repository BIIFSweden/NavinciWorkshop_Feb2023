setImageType('BRIGHTFIELD_H_DAB');
setPixelSizeMicrons(0.352000, 0.352000)
setColorDeconvolutionStains('{"Name" : "H-DAB estimated", "Stain 1" : "Hematoxylin", "Values 1" : "0.68336 0.70356 0.195", "Stain 2" : "DAB", "Values 2" : "0.26108 0.76025 0.59486", "Background" : " 195 196 193"}');
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Hematoxylin OD","requestedPixelSizeMicrons":0.5,"backgroundRadiusMicrons":8.0,"backgroundByReconstruction":true,"medianRadiusMicrons":5.0,"sigmaMicrons":1.5,"minAreaMicrons":100.0,"maxAreaMicrons":400.0,"threshold":0.03,"maxBackground":2.0,"watershedPostProcess":false,"excludeDAB":false,"cellExpansionMicrons":20.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')
runPlugin('qupath.imagej.detect.cells.SubcellularDetection', '{"detection[DAB]":0.04,"doSmoothing":false,"splitByIntensity":true,"splitByShape":false,"spotSizeMicrons":1.0,"minSpotSizeMicrons":0.5,"maxSpotSizeMicrons":20.0,"includeClusters":true}')