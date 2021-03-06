package br.unioeste.foz.cc.tcc.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unioeste.foz.cc.tcc.infra.QueryMakerSingleton;
import br.unioeste.foz.cc.tcc.model.demonstracao.AtributoValor;
import br.unioeste.foz.cc.tcc.model.demonstracao.RelatorioAnual;

public class RelatorioAnualDAO {

	private QueryMakerSingleton queryMaker;

	public RelatorioAnualDAO() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		queryMaker = QueryMakerSingleton.getInstance();
	}

	public int inserir(RelatorioAnual relatorioAnual, int idEmpresa) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		String columns = "idrelatorioAnual, finalPeriodo, empresa_idempresa";

		Object[] values = { getNextId(), relatorioAnual.getFinalPeriodo(), idEmpresa };

		int idRelatorio = queryMaker.insert("relatorioAnual", columns, values);

		AtributoValorDAO atrvle = new AtributoValorDAO();
		AtributoDAO atributoDAO = new AtributoDAO();
		for(AtributoValor atr : relatorioAnual.getAtributoValor()){
			atr.setIdAtributo(atributoDAO.inserir(atr.getAtributo()));
			atr.setIdRelatorioAnual(idRelatorio);
			atrvle.inserir(atr);
		}

		return idRelatorio;
	}

	public void alterar(RelatorioAnual relatorioAnual, int idEmpresa) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		
		relatorioAnual.setId(existe(relatorioAnual, idEmpresa));
		
		String[] columns = { "finalPeriodo" };

		Object[] values = { relatorioAnual.getFinalPeriodo() };

		queryMaker.updateWhere("relatorioAnual", columns,
				"idrelatorioAnual = ?", values, relatorioAnual.getId());
		
		AtributoValorDAO atrDAO = new AtributoValorDAO();
		AtributoDAO aDAO = new AtributoDAO();
		for(AtributoValor atr : relatorioAnual.getAtributoValor()){
			atr.setIdAtributo(aDAO.inserir(atr.getAtributo()));
			atr.setIdRelatorioAnual(relatorioAnual.getId());
			atrDAO.alterar(atr);
		}
		
	}

	protected int existe(RelatorioAnual relatorioAnual, int idEmpresa) throws SQLException {
		ResultSet rs = queryMaker.selectWhere("relatorioAnual", "idrelatorioAnual",
				"finalPeriodo = ? and " + "empresa_idempresa = '" + idEmpresa + "'", relatorioAnual.getFinalPeriodo());
		return rs.getInt(1);	
	}

	public void remover(RelatorioAnual relatorioAnual) throws SQLException {
		queryMaker.deleteWhere("relatorioAnual", "idrelatorioAnual = ?",
				relatorioAnual.getId());
	}

	public RelatorioAnual obter(int id) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		AtributoValorDAO atrDAO = new AtributoValorDAO();

		ResultSet rs = queryMaker.selectAllWhere("relatorioAnual",
				"idrelatorioAnual = ?", id);
		RelatorioAnual relatorioAnual = new RelatorioAnual(rs.getDate(2));
		relatorioAnual.setId(id);

		relatorioAnual.setAtributoValor(atrDAO.obterAtributos(rs
				.getInt("idrelatorioanual")));

		return relatorioAnual;
	}

	private int getNextId() throws SQLException {
		return queryMaker.selectSequence("idrelatorioAnual");
	}

	public List<RelatorioAnual> obterRelatorios(int idEmpresa) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {

		List<RelatorioAnual> relatorios = new ArrayList<RelatorioAnual>();

		AtributoValorDAO atrDAO = new AtributoValorDAO();

		ResultSet rs = queryMaker.selectAllWhere("relatorioanual",
				"empresa_idempresa = ?", idEmpresa);

		relatorios.add(new RelatorioAnual(rs.getInt("idrelatorioanual"), rs
				.getDate("finalperiodo"), atrDAO.obterAtributos(rs
				.getInt("idrelatorioanual"))));
		while (rs.next())
			relatorios.add(new RelatorioAnual(rs.getInt("idrelatorioanual"), rs
					.getDate("finalperiodo"), atrDAO.obterAtributos(rs
					.getInt("idrelatorioanual"))));

		return relatorios;
	}

}
