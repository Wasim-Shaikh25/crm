package com.synterra.lens.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synterra.lens.entity.MasterTableForDrawing;
import com.synterra.lens.repository.MastertableForDrawingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MastertableForDrawingService {

	private final MastertableForDrawingRepository drawingRepository;

	public List<MasterTableForDrawing> getAllMastertableData() {
		return drawingRepository.findAll();
	}

	public List<String> getMastertableDataByColumnName(String columnName) {
		return drawingRepository.findByColumnName(columnName);
	}
}