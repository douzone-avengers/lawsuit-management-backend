package com.avg.lawsuitmanagement.lawsuit.dto;

<<<<<<< HEAD
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
=======
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
>>>>>>> 706f72627b1a319af358549837d248131de78f06

@Getter
@Setter
@Builder
<<<<<<< HEAD
@ToString
public class LawsuitDto {
    private String lawsuitType;
    private String name;
    private int courtId;   // 담당법원 -> int로 받을지 string으로 받을지?
    private int commissionFee; // 의뢰비
    private int contingentFee; // 성공보수비용
    private String lawsuitStatus;
    private String lawsuitNum;
    private String result;
    private Date judgementDate;
    private Date createAt;
    private Date updatedAt;
    private boolean isDeleted;
=======
@RequiredArgsConstructor
public class LawsuitDto {
    private String lawsuit_type;
    private String name;
    private int court_id;
    private int commission_fee;
    private int contingent_fee;
    private String lawsuit_status;
    private String lawsuit_num;
    private String result;
    private String judgement_date;
    private String created_at;
    private String updated_at;
>>>>>>> 706f72627b1a319af358549837d248131de78f06
}
