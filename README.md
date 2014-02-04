AVP_FSERVER
===========

Whith ff-mpeg
Протокол обмена сообщениями.
Начальный вариант
Команды от системы управления(СУ) серверу
1) startVideoRecording
2) stopVideoRecording
3) cutVideoRecord -> addChapterTag
4) startPrezentRecord
5) stopPrezentRecord
6) cutPrezentRecord
7) getRecordings
8) getListOfPrezent
9) recorderRecordOnly
10) recorderTransmitOnly
11) recorderRecordAndTransmit
12) 

Сообщения от сервера СУ
1) info1 - Record started
2) info2 - Record stopped
3) info3 - Record cutted
4) info4 - Prezent started
5) info5 - Prezent stopped
6) info6 - Record only set
7) info7 - Transmit only set
8) info8 - RecordAndTransmit mode set

13) err1 - Record not started
14) err2 - Record not stoped
14) err3 - Mode not setted
15) err4 - No signal
16) err5 - No place on disk
17) err6 - Prezent not started
18) err7 - Prezent not started
