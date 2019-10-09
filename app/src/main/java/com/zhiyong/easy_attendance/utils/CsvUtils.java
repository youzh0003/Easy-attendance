package com.zhiyong.easy_attendance.utils;

import android.os.Environment;

import com.opencsv.CSVWriter;
import com.zhiyong.easy_attendance.consts.Consts;
import com.zhiyong.easy_attendance.data.models.Record;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CsvUtils {
    public static void writeRecordsToCsv(List<Record> recordList, String dateString, String timezone) throws IOException {
        String groupName = Consts.Empty;
        if(recordList != null && recordList.size() > 0 &&
                recordList.get(0) != null && recordList.get(0).getPerson() != null &&
                recordList.get(0).getPerson().getGroup() != null && recordList.get(0).getPerson().getGroup().getName() != null){
            groupName = recordList.get(0).getPerson().getGroup().getName();
        }

        String baseDir = android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String fileName = String.format(Locale.getDefault(), "%1$s_%2$s", groupName, dateString)+".csv";
        String filePath = baseDir + File.separator + fileName;
        File f = new File(filePath);
        CSVWriter writer = null;

        // File exist
        if(f.exists()&&!f.isDirectory())
        {
            // For delete and create new
            f.delete();
            writer = new CSVWriter(new FileWriter(filePath));

            // For append
//            FileWriter mFileWriter = null;
//            mFileWriter = new FileWriter(filePath, true);
//            writer = new CSVWriter(mFileWriter);
        }
        else
        {
            writer = new CSVWriter(new FileWriter(filePath));

        }

        String[] header = {"Name", "Group", "Date", "Type"};
        writer.writeNext(header);
        for (Record record:recordList
             ) {
            String name = record.getPerson()!= null && record.getPerson().getName() != null ? record.getPerson().getName() : Consts.Empty;
            String group = record.getPerson()!= null && record.getPerson().getGroup() != null &&
                    record.getPerson().getGroup().getName()!= null ? record.getPerson().getGroup().getName() : Consts.Empty;
            String date = DateUtils.formatDateTime(record.getRecordedAt(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS_U, timezone);
            String type = record.getType() != null ? record.getType() : Consts.Empty;
            String[] data = {name, group, date, type};
            writer.writeNext(data);
        }
        writer.close();
    }
}
