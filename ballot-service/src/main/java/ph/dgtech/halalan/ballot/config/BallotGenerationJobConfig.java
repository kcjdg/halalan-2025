package ph.dgtech.halalan.ballot.config;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import ph.dgtech.halalan.ballot.dto.VotersBallotDetails;
import ph.dgtech.halalan.ballot.model.primary.Ballot;
import ph.dgtech.halalan.ballot.model.secondary.User;
import ph.dgtech.halalan.ballot.service.ElectionService;

import javax.sql.DataSource;

@Configuration
public class BallotGenerationJobConfig {

    @Qualifier("firstDataSource")
    @Autowired
    private DataSource firstDataSource;

    @Qualifier("secondDataSource")
    @Autowired
    private DataSource secondDataSource;

    @Autowired
    private PlatformTransactionManager firstTransactionManager;

    @Autowired
    private ElectionService electionService;

    @Bean
    public Job generateBallotJob(JobRepository jobRepository) {

        return new JobBuilder("generateBallotJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(insertStep(jobRepository, firstDataSource))
                .build();

    }

    @Bean
    public Step insertStep(JobRepository jobRepository, DataSource dataSource) {
        return new StepBuilder("insertStep", jobRepository)
                .<User, VotersBallotDetails>chunk(10, new DataSourceTransactionManager(dataSource))
                .reader(reader())
                .processor(itemProcessor())
                .writer(writer())
                .transactionManager(firstTransactionManager)
                .build();
    }

    @Bean
    public JdbcCursorItemReader<User> reader() {
        JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(secondDataSource);
        reader.setSql("SELECT  id, username, first_name, last_name, email FROM user_entity");
        reader.setRowMapper(new BeanPropertyRowMapper<>(User.class));
        return reader;
    }


    @Bean
    public ItemProcessor<User, VotersBallotDetails> itemProcessor() {
        return person -> {
            var electionDto = electionService.getLastActiveElection();
            return new VotersBallotDetails(
                    person.getId(),
                    electionDto.electionId(),
                    electionDto.electionDate(),
                    electionDto.electionType(),
                    "electoralDivision"
            );
        };
    }


    @Bean
    public JdbcBatchItemWriter<VotersBallotDetails> writer() {
        JdbcBatchItemWriter<VotersBallotDetails> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(firstDataSource);
        writer.setSql("INSERT INTO t_ballots (ballot_id, voter_id, election_id, election_date,district_id,electoral_division ) VALUES (UUID(), :voterId, :electionId, :electionDate, :districtId, :electoralDivision)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }


}
