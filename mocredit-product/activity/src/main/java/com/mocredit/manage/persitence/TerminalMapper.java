package com.mocredit.manage.persitence;

import java.util.List;

import com.mocredit.manage.model.Terminal;

public interface TerminalMapper {
	List<Terminal> selectAll(Terminal terminal);
}