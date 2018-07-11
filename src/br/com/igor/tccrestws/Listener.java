package br.com.igor.tccrestws;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import br.com.igor.tccrestws.dao.AtividadeDao;
import br.com.igor.tccrestws.dao.UsuarioAtividadeDao;
import br.com.igor.tccrestws.vo.AtividadeVo;

public class Listener implements javax.servlet.ServletContextListener {


	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		Timer uploadCheckerTimer = new Timer(true);
		uploadCheckerTimer.scheduleAtFixedRate( new TimerTask() {
			
			@Override
			public void run() {
				UsuarioAtividadeDao uaDao = new UsuarioAtividadeDao();
				AtividadeDao aDao = new AtividadeDao();
				List<AtividadeVo> listaAtividades = aDao.selectAtividades(new AtividadeVo());
				Integer quantidade = 50;
				for(AtividadeVo vo :listaAtividades){
	    			if(uaDao.countAtividade(vo.getAtividade())> quantidade){
	    				aDao.updateAtividade(vo.getAtividade());
	    			}
    			}
			} 
		},0, 60 * 60 * 1000);
	}
}
