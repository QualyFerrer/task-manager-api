package com.cesar.task_manager_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cesar.task_manager_api.dto.TaskRequestDto;
import com.cesar.task_manager_api.dto.TaskResponseDto;
import com.cesar.task_manager_api.enums.Priority;
import com.cesar.task_manager_api.exception.TaskNotFoundException;
import com.cesar.task_manager_api.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TaskController.class) // sobe só a camada web
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc; // simula requisições HTTP

    @MockBean
    TaskService taskService; // mock do service

    @Autowired
    ObjectMapper objectMapper; // converte objetos para JSON

    private TaskResponseDto responseDto;
    private TaskRequestDto requestDto;

    @BeforeEach
    void setUp() {
        responseDto = new TaskResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Estudar Spring");
        responseDto.setDescription("Praticar REST");
        responseDto.setCompleted(false);
        responseDto.setPriority(Priority.HIGH);

        requestDto = new TaskRequestDto();
        requestDto.setTitle("Estudar Spring");
        requestDto.setDescription("Praticar REST");
        requestDto.setPriority(Priority.HIGH);
    }

    @Test
    void deveCriarTarefaERetornar201() throws Exception {
        when(taskService.create(any(TaskRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Estudar Spring"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void deveRetornar404QuandoTarefaNaoExistir() throws Exception {
        when(taskService.findById(99L)).thenThrow(new TaskNotFoundException(99L));

        mockMvc.perform(get("/tasks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void deveRetornar400QuandoTituloEstiverVazio() throws Exception {
        requestDto.setTitle(""); // título inválido

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar204AoDeletarTarefa() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());
    }
}