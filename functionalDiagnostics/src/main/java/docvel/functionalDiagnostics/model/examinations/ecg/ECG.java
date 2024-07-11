package docvel.functionalDiagnostics.model.examinations;

import docvel.functionalDiagnostics.providers.PatientsProvider;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ECG extends FunctionalDiagnosticsExam implements Serializable {

    private float speed;
    private Direct directP;
    private float waveP;
    private float intervalPQ;
    private float complexQRS;
    private float intervalQT;
    private float intervalRR;
    private float resultVectorSizeI;
    private float resultVectorSizeAVF;
    private double alphaAngle;
    private float heartRate;
    private String eAxisOfHeart;
    private double QTc;
    private String conclusion;
    private PatientsProvider patsProvider;

    public ECG(float speed, Direct directP, float pWaveSize,
               float pqIntervalSize, float qrsComplexSize,
               float qtIntervalSize, float rrIntervalSize,
               float resultVectorSizeI, float resultVectorSizeAVF,
               String conclusion) {
        super.setNameOfExam("Электрокардиография");
        this.speed = speed;
        this.directP = directP;
        this.waveP = pWaveSize;
        this.intervalPQ = pqIntervalSize;
        this.complexQRS = qrsComplexSize;
        this.intervalQT = qtIntervalSize;
        this.intervalRR = rrIntervalSize;
        this.resultVectorSizeI = resultVectorSizeI;
        this.resultVectorSizeAVF = resultVectorSizeAVF;
        this.heartRate = Math.round(60 / (rrIntervalSize / speed));
        this.alphaAngle = calculatingAlphaAngle(resultVectorSizeI, resultVectorSizeAVF);
        this.eAxisOfHeart = definitionElectricalAxisOfHeart(this.alphaAngle);
        this.QTc = correctQTCalculation(this.heartRate,
                rrIntervalSize / speed,
                qtIntervalSize / speed);
        this.conclusion = conclusion;
    }

    private double calculatingAlphaAngle(float xI, float yAVF){
        alphaAngle = Math.toDegrees(Math.atan(Math.abs(yAVF / xI)));
        if(xI < 0 && yAVF > 0) return 180 - alphaAngle;
        if(xI > 0 && yAVF < 0) return alphaAngle * -1;
        if(xI < 0 && yAVF < 0) return (180 - alphaAngle) * -1;
        return alphaAngle;
    }

    private String definitionElectricalAxisOfHeart(double angleAlfa){
        if (angleAlfa >= 90.0 ) return "отклонена вправо";
        if (angleAlfa >= 70.0) return "вертикальное положение";
        if (angleAlfa >= 30.0) return "нормальное положение";
        if (angleAlfa >= 0.0) return "горизонтальное положение";
        if (angleAlfa >= -70.0) return "отклонена влево";
        return "резко отклонена влево";
    }

    private double correctQTCalculation(float heartRate, float intervalRR, float intervalQT){
        if(heartRate < 60 || heartRate >= 90){
            return ((intervalQT * 1000) + (0.154 * (1 - intervalRR * 1000)) * 1000);
        }
        return ((intervalQT * 1000) / Math.sqrt(intervalRR));
    }

    @Override
    public String toString(){
        @SuppressWarnings("StringBufferReplaceableByString")
        StringBuilder sb = new StringBuilder(getNameOfExam()).append("\n")
                .append("скорость движения ленты: ").append(getSpeed()).append("мм/сек\n")
                .append("направление P: ").append(getDirectP()).append(", ")
                .append("P: ").append((getWaveP() / getSpeed()) * 1000).append("мсек\n")
                .append("QRS: ").append((getComplexQRS() / getSpeed()) * 1000).append("мсек\n")
                .append("QT: ").append((getIntervalQT() / getSpeed()) * 1000).append("мсек, ")
                .append("QTc: ").append(getQTc()).append("мсек\n")
                .append("RR: ").append((getIntervalRR() / getSpeed()) * 1000).append("мсек, ")
                .append("ЧСС: ").append(getHeartRate()).append("уд/мин\n")
                .append("Угол α: ").append(getAlphaAngle()).append("°, ")
                .append("ЭОС: ").append(getEAxisOfHeart()).append("\n")
                .append("Заключение: ").append(getConclusion());
        return sb.toString();
    }
}
