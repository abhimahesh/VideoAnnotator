
# coding: utf-8

# In[1]:


import sys
import cv2


# In[2]:


class util(object):
    def __init__(self):
        (self.major_ver, self.minor_ver, self.subminor_ver) = (cv2.__version__).split('.')
    def read_arg_from_file(self):
        #file = open("/home/amahesh/eclipse-workspace/VideoAnnotator/.vat/bufferFiles/output_java.txt","r")
        file = open(sys.argv[1]+"/.vat/bufferFiles/output_java.txt","r")
        arguments = file.readline()
        arg_array = arguments.split()
        self.path = arg_array[0]
        self.curr_pos = arg_array[1]
        self.tracker_id = arg_array[2]
        self.given_rect = (float(arg_array[3]),float(arg_array[4]),float(arg_array[5]),float(arg_array[6]))
        
    def read_all_name_list(self):
        #imagelistFile = open("/home/amahesh/eclipse-workspace/VideoAnnotator/.vat/bufferFiles/metadata.txt","r");
        imagelistFile = open(sys.argv[1]+"/.vat/bufferFiles/metadata.txt","r");
        imagelistFiles = imagelistFile.readline()
        self.imagelist = imagelistFiles.split()
    def trackerFunc(self):
        tracker_types = ['BOOSTING', 'MIL','KCF', 'TLD', 'MEDIANFLOW', 'GOTURN']
        tracker_type = tracker_types[int(self.tracker_id)]
        if int(self.minor_ver) < 3:
            tracker = cv2.Tracker_create(tracker_type)
        else:
            if tracker_type == 'BOOSTING':
                tracker = cv2.TrackerBoosting_create()
            if tracker_type == 'MIL':
                tracker = cv2.TrackerMIL_create()
            if tracker_type == 'KCF':
                tracker = cv2.TrackerKCF_create()
            if tracker_type == 'TLD':
                tracker = cv2.TrackerTLD_create()
            if tracker_type == 'MEDIANFLOW':
                tracker = cv2.TrackerMedianFlow_create()
            if tracker_type == 'GOTURN':
                tracker = cv2.TrackerGOTURN_create()
#         print(self.path+"/"+"data/"+self.imagelist[int(self.curr_pos)])
        frame = cv2.imread(self.path+"/"+self.imagelist[int(self.curr_pos)],cv2.IMREAD_COLOR)
#         cv2.imshow("FirstFrame",frame)
#         cv2.waitKey(2000)    
#         self.given_rect = cv2.selectROI(frame,False)
        ok = tracker.init(frame, self.given_rect)
#         print("ok")
        #outputFile_python_to_java = "/home/amahesh/eclipse-workspace/VideoAnnotator/.vat/bufferFiles/output_python.txt";
        outputFile_python_to_java = sys.argv[1]+"/.vat/bufferFiles/output_python.txt";
        outputFile = open(outputFile_python_to_java,"w+")

        for item in self.imagelist[int(self.curr_pos)+1: ]:
            frame = cv2.imread(self.path+"/"+item)
            ok, bbox = tracker.update(frame)
            ##Write to file and Display images
            if ok:
                outputFile.write(""+item+" "+str(bbox[0])+" "+str(bbox[1])+" "+str(bbox[2])+" "+str(bbox[3])+"\n")
                #print(""+item+" "+str(bbox[0])+" "+str(bbox[1])+" "+str(bbox[2])+" "+str(bbox[3])+"\n")
#                 p1 = (int(bbox[0]), int(bbox[1]))
#                 p2 = (int(bbox[0] + bbox[2]), int(bbox[1] + bbox[3]))
#                 cv2.rectangle(frame, p1, p2, (255,0,0), 2, 1)
            else:
#                 cv2.putText(frame, "Tracking Failure Detected", (100, 80),  cv2.FONT_HERSHEY_SIMPLEX, 0.75,(0,0,255),2)
                break
            
#             cv2.imshow("Trackingg", frame)
#             cv2.waitKey(300)
        outputFile.close()  
obj = util()
obj.read_arg_from_file()
obj.read_all_name_list()
obj.trackerFunc()


# In[ ]:




