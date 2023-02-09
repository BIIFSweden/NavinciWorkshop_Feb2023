def annotations = getAnnotationObjects()
def newDetections = annotations.collect {
    return PathObjects.createCellObject(it.getROI(),it.getROI(), it.getPathClass(), it.getMeasurementList())
}
removeObjects(annotations, true)
addObjects(newDetections)