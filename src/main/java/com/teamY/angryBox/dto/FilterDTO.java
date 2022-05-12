package com.teamY.angryBox.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
//@Getter
//@AllArgsConstructor
public class FilterDTO {
    String startDate = "1";
    String endDate = "1";
    int angry[];
    int imageFilter = 2; // 0 : 이미지 없는거만, 1: 이미지 있는거만, 2 : 이미지 있/없 둘다,

    public FilterDTO(String startDate, String endDate, int angry[], int imageFilter) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.angry = angry;
        this.imageFilter = imageFilter;
    }

    public StringBuilder getFilter() {
        StringBuilder stringBuilder = new StringBuilder();

        if(startDate != null && endDate != null){
            stringBuilder.append("(DATE_FORMAT(write_date, '%Y-%m-%d') >= '" + startDate + "' and DATE_FORMAT(write_date, '%Y-%m-%d') <= '" + endDate + "')");
        }


        if(angry != null && angry.length > 0) {
            stringBuilder.append(" AND (");
            for(int i : angry) {
                stringBuilder.append("angry_phase_id = " + i + " OR ");
            }
            stringBuilder.delete(stringBuilder.length()-4, stringBuilder.length());
            stringBuilder.append(")");
        }

        if(imageFilter != 2) {
            stringBuilder.append(" AND ");
            if(imageFilter == 1)
                stringBuilder.append("id IN (SELECT DISTINCT diary_id FROM diary_file)");
            else if(imageFilter == 0)
                stringBuilder.append("not id IN (SELECT DISTINCT diary_id FROM diary_file)");
        }

        return stringBuilder;
    }
}
