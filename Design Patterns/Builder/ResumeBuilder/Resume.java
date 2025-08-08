import java.util.ArrayList;
import java.util.List;

public class Resume {
    private final String name;
    private final String email;
    private final List<String> education;
    private final List<String> experience;
    private final List<String> skills;

    public Resume(String name, String email, List<String> education, List<String> experience, List<String> skills) {
        this.name = name;
        this.email = email;
        this.education = education;
        this.experience = experience;
        this.skills = skills;
    }

    static class ResumeBuilder {
        public interface NameStep {
            EmailStep setName(String name);
        }

        public interface EmailStep {
            OptionalSteps setEmail(String email);
        }
        
        public interface OptionalSteps {
            OptionalSteps addEducation(String education);
            OptionalSteps addExperience(String experience);
            OptionalSteps addSkill(String skills);
            BuildStep noMoreOptionalElements();
        }

        public interface BuildStep {
            Resume build();
        }
        
        public static NameStep builder() {
            return new Steps();
        }

        public static class Steps implements NameStep, EmailStep, OptionalSteps, BuildStep {
            private String name;
            private String email;
            private List<String> education;
            private List<String> experience;
            private List<String> skills;

            public Steps() {
                education = new ArrayList<>();
                experience = new ArrayList<>();
                skills = new ArrayList<>();
            }

            @Override
            public Resume.ResumeBuilder.EmailStep setName(String name) {
                this.name = name;
                return this;
            }

            @Override
            public Resume.ResumeBuilder.OptionalSteps setEmail(String email) {
                this.email = email;
                return this;
            }

            @Override
            public Resume.ResumeBuilder.BuildStep noMoreOptionalElements() {
                return this;
            }

            @Override
            public Resume.ResumeBuilder.OptionalSteps addEducation(String education) {
                this.education.add(education);
                return this;
            }

            @Override
            public Resume.ResumeBuilder.OptionalSteps addExperience(String experience) {
                this.experience.add(experience);
                return this;
            }

            @Override
            public Resume.ResumeBuilder.OptionalSteps addSkill(String skills) {
                this.skills.add(skills);
                return this;
            }     
            
            @Override
            public Resume build() {
                return new Resume(name, email, education, experience, skills);
            }
        }
    }

    @Override
    public String toString() {
        return "Resume [name=" + name + ", email=" + email + ", education=" + education + ", experience=" + experience
                + ", skills=" + skills + "]";
    }
}
