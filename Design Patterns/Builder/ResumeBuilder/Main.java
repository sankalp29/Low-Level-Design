public class Main {
    public static void main(String[] args) {
        Resume resume = Resume.ResumeBuilder.builder()
                        .setName("Sankalp")
                        .setEmail("email@email.com")
                        .addEducation("BIT Mesra")
                        .addExperience("Amazon SDE")
                        .addSkill("DSA")
                        .addSkill("High Level Design")
                        .addSkill("Low Level Design")
                        .addSkill("Java")
                        .addSkill("Spring Boot")
                        .addSkill("React")
                        .addSkill("etc...")
                        .noMoreOptionalElements()
                        .build();
        
        System.out.println(resume);
    }
}
