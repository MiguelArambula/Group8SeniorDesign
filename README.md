Project PhatLab
=========================================
**Project Title:** *PhatLab*   
**Group Title:** *Team 4-bits of 8 (Working Title)*   

**Group Members:**
*   Jake Harper
*   Miguel Arambula
*   Patrick Weingardt
*   Reuben Shea

**Project Description:**   
  The PhatLab is an on-the-fly mobile audio workstation that runs on Andriod, with the goal of providing an easy yet effective way of capturing musical inspiration. This project does not aim to become a platform for production-level exports, but rather a means to act as a relatively quick compositional tool and/or loop station, the latter only if time permits.  
  
  This piece of software will contain a means to loop audio samples that are placed via a sequencer, an interactive sample pad where you can trigger samples using touch, as well as a means to record and insert personal audio samples. We are aiming for an intuative and effective interface that splits the screen into a local and global interface, the local interface switching between the sequencer and trigger pads, while the global interface handles items such as BPM and other project-related settings.
  
**Important Files / Documents:**
* /Source/PhatLab/src/edu/cosc4950/phatlab/
The above folder contains all the important source files.

=========================================
**Classes:** 

*ExternalData:* 
The ExternalData class handles all the file loading and exporting. Essentially any data that needs to be handled that is not directily part of the Java codebase is handled through this class.

*PCM:* 
The PCM class represents an audio sample. It contains all the byte data for a single sample as well as any properties that are required. This class also provides the functions to manipulate its data, such as resampling and adjusting gain.

*Recorder:* 
The Recorder class is responsible for reading data in from the mic. The class essentially is triggered to stream in data, and it is written into a buffer. Once all the data is recorded, it creates a new PCM to contain the audio.

*SequenceTimer:* 
The SequenceTimer class is responsible for the sequencing backend. It controls the actual timer and triggers the audio samples. It is also responsible for the merging of multiple samples into one large sample and passing the result to ExternalData for export.
