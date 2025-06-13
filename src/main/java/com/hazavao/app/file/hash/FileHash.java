package com.hazavao.app.file.hash;

import com.hazavao.app.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}
