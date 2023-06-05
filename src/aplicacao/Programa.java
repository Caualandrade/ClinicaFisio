package aplicacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dao.ConsultaDao;
import dao.EnderecoDao;
import dao.EspecialidadeDao;
import dao.FisioterapeutaDao;
import dao.PacienteDao;
import dao.UnidadeDao;
import entidades.Consulta;
import entidades.Endereco;
import entidades.Especialidade;
import entidades.Fisioterapeuta;
import entidades.Paciente;
import entidades.Unidade;
import implementacaoDao.DaoFactory;

public class Programa {

	public static void main(String[] args) throws ParseException {

		ConsultaDao consultaDao = DaoFactory.createConsultaDao();
		EnderecoDao enderecoDao = DaoFactory.createEnderecoDao();
		FisioterapeutaDao fisioDao = DaoFactory.createFisioterapeutaDao();
		PacienteDao pacienteDao = DaoFactory.createPacienteDao();
		UnidadeDao unidadeDao = DaoFactory.createUnidadeDao();
		EspecialidadeDao especialidadeDao = DaoFactory.createEspecialidadeDao();

		int resp = 0;

		Scanner sc = new Scanner(System.in);

		SimpleDateFormat sdfBrasil = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat sdfBanco = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfBancoDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfBrasilDate = new SimpleDateFormat("dd/MM/yyyy");

		do {

			System.out.println("---- SISTEMA NEOFISIO ----");
			System.out.println("1 - AGENDAR CONSULTA\n2 - VER CONSULTAS\n3 - ACESSO DA CLINICA\n0 - Sair");
			resp = sc.nextInt();
			switch (resp) {
			case 1:
				Consulta consulta = new Consulta();

				System.out.println("Selecione a unidade onde será realizada a consulta");
				List<Unidade> listaUnidade = unidadeDao.findAll();
				for (Unidade u : listaUnidade) {
					System.out.println(u);
				}
				resp = sc.nextInt();
				Unidade unidade = unidadeDao.findById(resp);
				consulta.setUnidade(unidade);

				sc.nextLine();
				System.out.println("Informe a data e hora da consulta(formato dd/MM/yyyy HH:mm:ss)");
				String dataHoraSt = sc.nextLine();
				Date dataHoraBr = sdfBrasil.parse(dataHoraSt);
				String dataHoraBanco = sdfBanco.format(dataHoraBr);
				consulta.setData_Hora(sdfBanco.parse(dataHoraBanco));
				System.out.println("Selecione a Fisioterapeuta responsavel pelo atendimento");
				List<Fisioterapeuta> listaFisio = fisioDao.findAll();
				for (Fisioterapeuta f : listaFisio) {
					System.out.println(f);
				}
				resp = sc.nextInt();
				Fisioterapeuta fisio = fisioDao.findById(resp);
				consulta.setFisioterapeuta(fisio);

				System.out.println("O paciente já tem cadastro na clinica? 1 - Sim 2 - Não");
				resp = sc.nextInt();
				if (resp == 1) {
					List<Paciente> listaPaciente = pacienteDao.findAll();
					for (Paciente pac : listaPaciente) {
						System.out.println(pac);
					}
					System.out.println("Selecione o paciente");
					resp = sc.nextInt();
					Paciente pac = pacienteDao.findById(resp);
					consulta.setPaciente(pac);
				} else {
					Paciente pac = new Paciente();
					Endereco end = new Endereco();

					System.out.println("Cadastro do paciente");
					sc.nextLine();
					System.out.print("Informe o nome: ");
					pac.setNome(sc.nextLine());
					System.out.print("Informe o sexo: ");
					pac.setSexo(sc.nextLine());
					System.out.print("Informe o telefone: ");
					pac.setTelefone(sc.nextLine());
					System.out.print("Informe a data de nascimento (formato dd/MM/yyyy): ");
					String dataNascimentoBrasil = sc.nextLine();
					Date date = sdfBrasilDate.parse(dataNascimentoBrasil);
					String dataBanco = sdfBancoDate.format(date);
					pac.setDataNascimento(sdfBancoDate.parse(dataBanco));
					System.out.print("Informe o logradouro: ");
					end.setLogradouro(sc.nextLine());
					System.out.print("Informe o bairro: ");
					end.setBairro(sc.nextLine());
					System.out.print("Informe a cidade: ");
					end.setCidade(sc.nextLine());
					System.out.print("Informe o numero do endereco: ");
					end.setNumEndereco(sc.nextInt());
					sc.nextLine();
					System.out.print("Informe o cep: ");
					end.setCep(sc.nextLine());
					enderecoDao.insert(end);
					Endereco endID = enderecoDao.findById(end.getId());
					pac.setEndereco(endID);
					pacienteDao.insert(pac);
					Paciente pacID = pacienteDao.findById(pac.getId());
					consulta.setPaciente(pacID);
				}
				System.out.println("### Consulta marcada com sucesso ###");
				consultaDao.insert(consulta);
				break;
			case 2:
				System.out.println("Consultas: ");
				List<Consulta> consultas = consultaDao.findAll();
				for (Consulta c : consultas) {
					System.out.println(c.consultaPerso());
				}

				break;
			case 3:
				System.out.println("---- INFORMAÇÕES INTERNAS ----");
				System.out.println("1 - Manipular Fisioterapeuta\n2 - Manipular Unidade\n"
						+ "3 - Manipular Especialidade\n4 - Manipular Paciente\n5 - Excluir Consulta\n6 - Sair");
				resp = sc.nextInt();
				switch (resp) {
				case 1:
					Fisioterapeuta fisioCad = new Fisioterapeuta();
					Especialidade esp = new Especialidade();
					Endereco end = new Endereco();

					System.out.println("1 - Cadastrar Fisioterapeuta\n2 - Excluir Fisioterapeuta\n"
							+ "3 - Lista de Fisioterapeuta\n4 - Editar Fisioterapeuta\n5 - Sair");
					resp = sc.nextInt();
					if (resp == 1) {
						sc.nextLine();
						System.out.print("Informe o nome: ");
						fisioCad.setNome(sc.nextLine());
						System.out.print("Informe a data de nascimento (formato dd/MM/yyyy): ");
						String dataNascimentoBrasil = sc.nextLine();
						Date date = sdfBrasilDate.parse(dataNascimentoBrasil);
						String dataBanco = sdfBancoDate.format(date);
						fisioCad.setDataDeNascimento(sdfBancoDate.parse(dataBanco));
						System.out.print("Informe o numero de registro: ");
						fisioCad.setNumeroRegistro(sc.nextLine());
						System.out.print("Informe o sexo: ");
						fisioCad.setSexo(sc.nextLine().toUpperCase());
						System.out.print("Informe o telefone: ");
						fisioCad.setTelefone(sc.nextLine());
						List<Especialidade> lista = especialidadeDao.findAll();
						for (Especialidade e : lista) {
							System.out.println(e);
						}
						System.out.print("Selecione a especilidade: ");
						Especialidade espID = especialidadeDao.findById(sc.nextInt());
						fisioCad.setEspecialidade(espID);
						sc.nextLine();
						System.out.print("Informe o logradouro: ");
						end.setLogradouro(sc.nextLine());
						System.out.print("Informe o bairro: ");
						end.setBairro(sc.nextLine());
						System.out.print("Informe a cidade: ");
						end.setCidade(sc.nextLine());
						System.out.print("Informe o numero do endereco: ");
						end.setNumEndereco(sc.nextInt());
						sc.nextLine();
						System.out.print("Informe o cep: ");
						end.setCep(sc.nextLine());
						enderecoDao.insert(end);
						fisioCad.setEndereco(end);
						fisioDao.insert(fisioCad);
						System.out.println("## Cadastro realizado com sucesso ##");
					} else if (resp == 2) {
						List<Fisioterapeuta> lista = fisioDao.findAll();
						for (Fisioterapeuta f : lista) {
							System.out.println(f);
						}
						System.out.println("Informe o id que deseja excluir");
						int id = sc.nextInt();
						fisioDao.deleteById(id);
						System.out.println("## Exclusão realizada com sucesso ##");
					} else if (resp == 3) {
						List<Fisioterapeuta> lista = fisioDao.findAll();
						for (Fisioterapeuta f : lista) {
							System.out.println(f);
						}
					} else if (resp == 4) {
						List<Fisioterapeuta> lista = fisioDao.findAll();
						for (Fisioterapeuta f : lista) {
							System.out.println(f);
						}
						System.out.println("Escolha qual fisioterapeuta alterar");
						int id = sc.nextInt();
						Fisioterapeuta fisioEscolha = fisioDao.findById(id);
						sc.nextLine();
						System.out.println("O que deseja alterar");
						String alt = sc.nextLine().toUpperCase();
						if (alt.equals("NOME")) {
							System.out.println("Informe o novo nome");
							String newName = sc.nextLine();
							fisioEscolha.setNome(newName);
							fisioDao.update(fisioEscolha);
						}
						System.out.println("## Alteração realizada com sucesso ##");
					}
					break;
				case 2:
					Unidade unidadeCad = new Unidade();
					Endereco endCad = new Endereco();
					System.out.println("1 - Cadastrar Unidade\n2 - Excluir Unidade\n"
							+ "3 - Lista de Unidades\n4 - Editar Unidade\n5 - Sair");
					resp = sc.nextInt();
					if (resp == 1) {
						sc.nextLine();
						System.out.println("Informe o nome da unidade");
						unidadeCad.setNome(sc.nextLine());
						System.out.print("Informe o logradouro: ");
						endCad.setLogradouro(sc.nextLine());
						System.out.print("Informe o bairro: ");
						endCad.setBairro(sc.nextLine());
						System.out.print("Informe a cidade: ");
						endCad.setCidade(sc.nextLine());
						System.out.print("Informe o numero do endereco: ");
						endCad.setNumEndereco(sc.nextInt());
						sc.nextLine();
						System.out.print("Informe o cep: ");
						endCad.setCep(sc.nextLine());
						enderecoDao.insert(endCad);
						unidadeCad.setEndereco(endCad);
						unidadeDao.insert(unidadeCad);
						System.out.println("Unidade cadastrada!");
					} else if (resp == 2) {
						List<Unidade> lista = unidadeDao.findAll();
						for (Unidade u : lista) {
							System.out.println(u);
						}
						System.out.println("Deseja excluir qual unidade?");
						int esc = sc.nextInt();
						unidadeDao.deleteById(esc);
						System.out.println("Unidade excluida com sucesso");
					} else if (resp == 3) {
						List<Unidade> lista = unidadeDao.findAll();
						for (Unidade u : lista) {
							System.out.println(u);
						}
					} else if (resp == 4) {

					}

					break;
				case 3:
					Especialidade espCad = new Especialidade();
					System.out.println("1 - Cadastrar Especialidade\n2 - Excluir Especialidade\n"
							+ "3 - Lista de Especialidade\n4 - Editar Especialidade\n5 - Sair");
					resp = sc.nextInt();
					if (resp == 1) {
						sc.nextLine();
						System.out.println("Informe o nome da especialidade");
						espCad.setNome(sc.nextLine());
						especialidadeDao.insert(espCad);
						System.out.println("Especialidade cadastrada com sucesso");
					} else if (resp == 2) {
						List<Especialidade> lista = especialidadeDao.findAll();
						for (Especialidade e : lista) {
							System.out.println(e);
						}
						System.out.println("Informe qual especialidade deseja excluir");
						especialidadeDao.deleteById(sc.nextInt());
						System.out.println("Especialidade excluida");
					} else if (resp == 3) {
						List<Especialidade> lista = especialidadeDao.findAll();
						for (Especialidade e : lista) {
							System.out.println(e);
						}
					} else if (resp == 4) {
						List<Especialidade> lista = especialidadeDao.findAll();
						for (Especialidade e : lista) {
							System.out.println(e);
						}
						System.out.println("Escolha a especialidade");
						int esc = sc.nextInt();
						Especialidade espEsc = especialidadeDao.findById(esc);
						sc.nextLine();
						System.out.println("Digite o novo nome");
						espEsc.setNome(sc.nextLine());
						especialidadeDao.update(espEsc);
						System.out.println("Especialidade alterada");
					}
					break;
				case 4:
					System.out.println("1 - Lista de Pacientes\n2 - Excluir Paciente\n" + "3 - Sair");
					resp = sc.nextInt();
					if (resp == 1) {
						List<Paciente> lista = pacienteDao.findAll();
						for (Paciente p : lista) {
							System.out.println(p);
						}
					} else if (resp == 2) {
						List<Paciente> lista = pacienteDao.findAll();
						for (Paciente p : lista) {
							System.out.println(p);
						}
						System.out.println("Informe qual paciente deseja excluir");
						pacienteDao.deleteById(sc.nextInt());
						System.out.println("Paciente excluido");
					}
					break;
				case 5:
					List<Consulta> lista = consultaDao.findAll();
					for(Consulta l: lista) {
						System.out.println(l.consultaPerso());
					}
					System.out.println("Deseja excluir qual consulta");
					consultaDao.deleteById(sc.nextInt());
					break;
				default:
					System.out.println("Opção inválida");
					break;
				}
				break;
			default:
				System.out.println("Sistema encerrado");
				break;
			}
		} while (resp != 0);

	}

}
