public class PrintJob {
    private String jobId;
    private String jobTitle;
    private String jobDesc;

    public PrintJob(String jobId, String jobTitle, String jobDesc) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobDesc = jobDesc;
    }

    @Override
    public String toString() {
        return "PrintJob [jobId=" + jobId + ", jobTitle=" + jobTitle + ", jobDesc=" + jobDesc + "]";
    }
}
