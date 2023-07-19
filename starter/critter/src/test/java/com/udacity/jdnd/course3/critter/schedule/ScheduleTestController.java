package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.boot.test.web.client.TestRestTemplate;

import java.time.DayOfWeek;
import java.util.*;

public class ScheduleTestController {

    private String baseUrl;
    private TestRestTemplate testRestTemplate;

    public ScheduleTestController(int port, TestRestTemplate testRestTemplate) {
        this.baseUrl = "http://localhost:" + port + "/schedule";
        this.testRestTemplate = testRestTemplate;
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        return testRestTemplate.postForObject(baseUrl, scheduleDTO, ScheduleDTO.class);
    }

    public List<ScheduleDTO> getAllSchedules() {
        return Arrays.stream(testRestTemplate.getForObject(baseUrl, ScheduleDTO[].class)).toList();
    }

    public List<ScheduleDTO> getScheduleForPet(long petId) {
        return Arrays.stream(testRestTemplate.getForObject(baseUrl + "/pet/" + petId, ScheduleDTO[].class)).toList();
    }

    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        return Arrays.stream(testRestTemplate.getForObject(baseUrl + "/employee/" + employeeId, ScheduleDTO[].class)).toList();
    }

    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
        return Arrays.stream(testRestTemplate.getForObject(baseUrl + "/customer/" + customerId, ScheduleDTO[].class)).toList();
    }

    public void deleteSchedule(long scheduleId) {
        testRestTemplate.delete(baseUrl + "/" + scheduleId);
    }

}
