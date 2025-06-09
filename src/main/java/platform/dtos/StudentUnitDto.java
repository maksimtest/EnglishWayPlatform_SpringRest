package platform.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentUnitDto {
    private int num;
    private String unitName;
    private List<Integer> hwScores = new ArrayList<>();
    private List<Integer> classScores = new ArrayList<>();
    private boolean isNew;
    private boolean isPaymentAgree;
}
