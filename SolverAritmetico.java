import java.util.List;
import java.util.ArrayList;

public class SolverAritmetico {

    public Nodo nodo;
    private List<variablesDeclaradas> varDecl = new ArrayList<>();

    public SolverAritmetico(Nodo nodo) {
        this.nodo = nodo;
    }

    public void resolver() {
        resolver(nodo);
    }

    private void resolver(Nodo n) {
        // Si es nulo es Raiz
        if (n.getValue() == null) {
            for (int i = n.getHijos().size() - 1; i >= 0; i--) {
                resolver(n.getHijos().get(i));
            }
        } else {
            if (n.getHijos() == null) {
                if (n.getValue().tipo == TipoToken.ID) {
                    boolean varExists = false;
                    for (variablesDeclaradas var : this.varDecl) {
                        if (var.nombre.equals(n.getValue().lexema)) {
                            varExists = true;
                            if (var.tipoToken == TipoToken.NUMBER) {
                                n.getValue().tipo = TipoToken.NUMBER;
                                n.getValue().lexema = ("" + var.valorNumerico).replace('"', Character.MIN_VALUE);
                            } else if (var.tipoToken == TipoToken.STRING) {
                                n.getValue().tipo = TipoToken.STRING;
                                n.getValue().lexema = ("" + var.valorTexto).replace('"', Character.MIN_VALUE);
                            }
                        }
                    }
                    if (!varExists) {
                        System.out.print("Variable " + n.getValue().lexema + " no se declaro antes de su utilizacion");
                        System.out.print("\n Linea: " + n.getValue().linea);
                        System.exit(0);
                    }
                }
            } else {
                switch (n.getValue().tipo) {
                    case PRINT:
                        resolverPrint(n);
                        break;
                    case EQUALS:
                        resolverIgual(n);
                        break;
                    case AND:
                        resolverY(n);
                        break;
                    case OR:
                        resolverO(n);
                        break;
                    case LESS_THAN:
                        resolverMenorQue(n);
                        break;
                    case GREATER_THAN:
                        resolverMayorQue(n);
                        break;
                    case LESS_OR_EQUAL_THAN:
                        resolverMenorOIgualQue(n);
                        break;
                    case GREATER_OR_EQUAL_THAN:
                        resolverMayorOIgualQue(n);
                        break;
                    case DIFFERENT_THAN:
                        resolverDiferenteQue(n);
                        break;
                    case EQUAL_THAN:
                        resolverIgualQue(n);
                        break;
                    case MINUS:
                        resolverResta(n);
                        break;
                    case PLUS:
                        resolverSuma(n);
                        break;
                    case SLASH:
                        resolverDivision(n);
                        break;
                    case ASTERISK:
                        resolverMultiplicacion(n);
                        break;
                }
            }
        }
    }

    private void resolverPrint(Nodo n) {
        if (n.getHijos().size() == 1) {
            if (n.getHijos().get(0).getHijos() != null || n.getHijos().get(0).getValue().tipo == TipoToken.ID) {
                resolver(n.getHijos().get(0));
                System.out.println(n.getHijos().get(0).getValue().lexema);
            } else if (n.getHijos().get(0).getValue().tipo == TipoToken.STRING
                    || n.getHijos().get(0).getValue().tipo == TipoToken.NUMBER) {
                System.out.println(n.getHijos().get(0).getValue().lexema);
            } else {
                System.out.println(n.getHijos().get(0).getValue().tipo + " no es soportado por print");
            }

        } else {
            if (n.getHijos().get(1).getHijos() != null) {
                resolver(n.getHijos().get(1));
            }
            if (n.getHijos().get(0).getHijos() != null) {
                resolver(n.getHijos().get(0));
            }
            if (n.getHijos().get(0).getValue().tipo == TipoToken.TRUE
                    || n.getHijos().get(0).getValue().tipo == TipoToken.FALSE) {
                if (n.getHijos().get(0).getValue().tipo == TipoToken.TRUE) {
                    System.out.println(n.getHijos().get(1).getValue().lexema);
                }
            } else {
                System.out.println("Algo salio mal con el print");
            }
        }
    }

    private void resolverIgual(Nodo n) {
        boolean varExists = false;
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        for (variablesDeclaradas var : this.varDecl) {
            if (var.nombre.equals(n.getHijos().get(0).getValue().lexema)) {
                varExists = true;
            }
        }
        if (!varExists && n.getHijos().get(1).getValue().tipo == TipoToken.NUMBER) {
            this.varDecl.add(
                    new variablesDeclaradas(n.getHijos().get(0).getValue().lexema.replace('"', Character.MIN_VALUE),
                            n.getHijos().get(1).getValue().tipo,
                            n.getHijos().get(1).getValue().lexema.replace('"', Character.MIN_VALUE),
                            null));
        } else if (!varExists && n.getHijos().get(1).getValue().tipo == TipoToken.STRING) {
            this.varDecl.add(
                    new variablesDeclaradas(n.getHijos().get(0).getValue().lexema.replace('"', Character.MIN_VALUE),
                            n.getHijos().get(1).getValue().tipo, null,
                            n.getHijos().get(1).getValue().lexema.replace('"', Character.MIN_VALUE)));
        } else if (varExists) {
            for (variablesDeclaradas var : this.varDecl) {
                if (var.nombre.replace('"', Character.MIN_VALUE)
                        .equals(n.getHijos().get(0).getValue().lexema.replace('"',
                                Character.MIN_VALUE))) {
                    var.tipoToken = n.getHijos().get(1).getValue().tipo;
                    if (var.tipoToken == TipoToken.STRING) {
                        var.valorNumerico = 0;
                        var.valorTexto = n.getHijos().get(1).getValue().lexema.replace('"', Character.MIN_VALUE);
                    } else if (var.tipoToken == TipoToken.NUMBER) {
                        var.valorTexto = null;
                        var.valorNumerico = n.getHijos().get(1).getValue().lexema.replace('"', Character.MIN_VALUE);
                    }
                }
            }
        }

    }

    private void resolverY(Nodo n) {
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(2).getHijos() != null) {
            resolver(n.getHijos().get(2));
        }
        if (n.getHijos().get(1).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(2).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(2));
        }

        if ((n.getHijos().get(1).getValue().tipo == TipoToken.FALSE
                || n.getHijos().get(1).getValue().tipo == TipoToken.TRUE)
                && (n.getHijos().get(2).getValue().tipo == TipoToken.FALSE
                        || n.getHijos().get(2).getValue().tipo == TipoToken.TRUE)) {
            if (n.getHijos().get(1).getValue().tipo == TipoToken.TRUE
                    && n.getHijos().get(2).getValue().tipo == TipoToken.TRUE) {
                n.getValue().tipo = TipoToken.TRUE;
                n.getValue().lexema = null;
            } else {
                n.getValue().tipo = TipoToken.FALSE;
                n.getValue().lexema = null;
            }
        } else {
            System.out.println(
                    "AND de " + n.getHijos().get(1).getValue().tipo + " "
                            + n.getHijos().get(2).getValue().tipo
                            + " no permitida");
            System.exit(0);
        }
    }

    private void resolverO(Nodo n) {
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(2).getHijos() != null) {
            resolver(n.getHijos().get(2));
        }
        if (n.getHijos().get(1).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(2).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(2));
        }

        if ((n.getHijos().get(1).getValue().tipo == TipoToken.FALSE
                || n.getHijos().get(1).getValue().tipo == TipoToken.TRUE)
                && (n.getHijos().get(2).getValue().tipo == TipoToken.FALSE
                        || n.getHijos().get(2).getValue().tipo == TipoToken.TRUE)) {
            if (n.getHijos().get(1).getValue().tipo == TipoToken.FALSE
                    && n.getHijos().get(2).getValue().tipo == TipoToken.FALSE) {
                n.getValue().tipo = TipoToken.FALSE;
                n.getValue().lexema = null;
            } else {
                n.getValue().tipo = TipoToken.TRUE;
                n.getValue().lexema = null;
            }
        } else {
            System.out.println(
                    "OR de " + n.getHijos().get(1).getValue().tipo + " "
                            + n.getHijos().get(2).getValue().tipo
                            + " no permitida");
            System.exit(0);
        }
    }

    private void resolverMenorQue(Nodo n) {
        if (n.getHijos().get(0).getHijos() != null) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(0).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(1));
        }

        if (n.getHijos().get(0).getValue().tipo == TipoToken.NUMBER
                && n.getHijos().get(1).getValue().tipo == TipoToken.NUMBER) {
            if (Double.parseDouble(n.getHijos().get(0).getValue().lexema) < Double
                    .parseDouble(n.getHijos().get(1).getValue().lexema)) {
                n.getValue().tipo = TipoToken.TRUE;
                n.getValue().lexema = null;
            } else {
                n.getValue().tipo = TipoToken.FALSE;
                n.getValue().lexema = null;
            }
        } else {
            System.out.println(
                    "Comparacion de " + n.getHijos().get(0).getValue().tipo + " "
                            + n.getHijos().get(1).getValue().tipo
                            + " no permitida");
            System.exit(0);
        }
    }

    private void resolverMayorQue(Nodo n) {
        if (n.getHijos().get(0).getHijos() != null) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(0).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(1));
        }

        if (n.getHijos().get(0).getValue().tipo == TipoToken.NUMBER
                && n.getHijos().get(1).getValue().tipo == TipoToken.NUMBER) {
            if (Double.parseDouble(n.getHijos().get(0).getValue().lexema) > Double
                    .parseDouble(n.getHijos().get(1).getValue().lexema)) {
                n.getValue().tipo = TipoToken.TRUE;
                n.getValue().lexema = null;
            } else {
                n.getValue().tipo = TipoToken.FALSE;
                n.getValue().lexema = null;
            }
        } else {
            System.out.println(
                    "Comparacion de " + n.getHijos().get(0).getValue().tipo + " "
                            + n.getHijos().get(1).getValue().tipo
                            + " no permitida");
            System.exit(0);
        }
    }

    private void resolverMenorOIgualQue(Nodo n) {
        if (n.getHijos().get(0).getHijos() != null) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(0).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(1));
        }

        if (n.getHijos().get(0).getValue().tipo == TipoToken.NUMBER
                && n.getHijos().get(1).getValue().tipo == TipoToken.NUMBER) {
            if (Double.parseDouble(n.getHijos().get(0).getValue().lexema) <= Double
                    .parseDouble(n.getHijos().get(1).getValue().lexema)) {
                n.getValue().tipo = TipoToken.TRUE;
                n.getValue().lexema = null;
            } else {
                n.getValue().tipo = TipoToken.FALSE;
                n.getValue().lexema = null;
            }
        } else {
            System.out.println(
                    "Comparacion de " + n.getHijos().get(0).getValue().tipo + " "
                            + n.getHijos().get(1).getValue().tipo
                            + " no permitida");
            System.exit(0);
        }
    }

    private void resolverMayorOIgualQue(Nodo n) {
        if (n.getHijos().get(0).getHijos() != null) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(0).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(1));
        }

        if (n.getHijos().get(0).getValue().tipo == TipoToken.NUMBER
                && n.getHijos().get(1).getValue().tipo == TipoToken.NUMBER) {
            if (Double.parseDouble(n.getHijos().get(0).getValue().lexema) >= Double
                    .parseDouble(n.getHijos().get(1).getValue().lexema)) {
                n.getValue().tipo = TipoToken.TRUE;
                n.getValue().lexema = null;
            } else {
                n.getValue().tipo = TipoToken.FALSE;
                n.getValue().lexema = null;
            }
        } else {
            System.out.println(
                    "Comparacion de " + n.getHijos().get(0).getValue().tipo + " "
                            + n.getHijos().get(1).getValue().tipo
                            + " no permitida");
            System.exit(0);
        }
    }

    private void resolverDiferenteQue(Nodo n) {
        if (n.getHijos().get(0).getHijos() != null) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(0).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(1));
        }

        if (n.getHijos().get(0).getValue().tipo == TipoToken.NUMBER
                && n.getHijos().get(1).getValue().tipo == TipoToken.NUMBER) {
            if (!n.getHijos().get(0).getValue().lexema.equals(n.getHijos().get(1).getValue().lexema)) {
                n.getValue().tipo = TipoToken.TRUE;
                n.getValue().lexema = null;
            } else {
                n.getValue().tipo = TipoToken.FALSE;
                n.getValue().lexema = null;
            }
        } else {
            System.out.println(
                    "Comparacion de " + n.getHijos().get(0).getValue().tipo + " "
                            + n.getHijos().get(1).getValue().tipo
                            + " no permitida");
            System.exit(0);
        }
    }

    private void resolverIgualQue(Nodo n) {
        if (n.getHijos().get(0).getHijos() != null) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(0).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(1));
        }

        if (n.getHijos().get(0).getValue().tipo == TipoToken.NUMBER
                && n.getHijos().get(1).getValue().tipo == TipoToken.NUMBER) {
            if (n.getHijos().get(0).getValue().lexema.equals(n.getHijos().get(1).getValue().lexema)) {
                n.getValue().tipo = TipoToken.TRUE;
                n.getValue().lexema = null;
            } else {
                n.getValue().tipo = TipoToken.FALSE;
                n.getValue().lexema = null;
            }
        } else {
            System.out.println(
                    "Comparacion de " + n.getHijos().get(0).getValue().tipo + " "
                            + n.getHijos().get(1).getValue().tipo
                            + " no permitida");
            System.exit(0);
        }
    }

    private void resolverResta(Nodo n) {
        if (n.getHijos().get(0).getHijos() != null) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(0).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(1));
        }

        if (n.getHijos().get(0).getValue().tipo == TipoToken.NUMBER
                && n.getHijos().get(1).getValue().tipo == TipoToken.NUMBER) {
            n.getValue().tipo = TipoToken.NUMBER;
            n.getValue().lexema = ""
                    + (Double.parseDouble(n.getHijos().get(0).getValue().lexema)
                            - Double.parseDouble(n.getHijos().get(1).getValue().lexema));
        } else {
            System.out.println(
                    "Resta de " + n.getHijos().get(0).getValue().tipo + " "
                            + n.getHijos().get(1).getValue().tipo
                            + " no permitida");
            System.exit(0);
        }

    }

    private void resolverSuma(Nodo n) {
        if (n.getHijos().get(0).getHijos() != null) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(0).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(0).getValue().tipo == n.getHijos().get(1).getValue().tipo) {
            if (n.getHijos().get(0).getValue().tipo == TipoToken.NUMBER) {
                n.getValue().tipo = TipoToken.NUMBER;
                n.getValue().lexema = ""
                        + (Double.parseDouble(n.getHijos().get(0).getValue().lexema)
                                + Double.parseDouble(n.getHijos().get(1).getValue().lexema));
            } else if (n.getHijos().get(0).getValue().tipo == TipoToken.STRING) {
                n.getValue().tipo = TipoToken.STRING;
                n.getValue().lexema = "" + n.getHijos().get(0).getValue().lexema.replace('"', Character.MIN_VALUE)
                        + n.getHijos().get(1).getValue().lexema.replace('"', Character.MIN_VALUE);
            } else {
                System.out.println(
                        "Suma de " + n.getHijos().get(0).getValue().tipo + " " + n.getHijos().get(1).getValue().tipo
                                + " no permitida");
                System.exit(0);
            }
        } else {
            System.out.println(
                    "Suma de " + n.getHijos().get(0).getValue().tipo + " " + n.getHijos().get(1).getValue().tipo
                            + " no permitida");
            System.exit(0);
        }

    }

    private void resolverDivision(Nodo n) {
        if (n.getHijos().get(0).getHijos() != null) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(0).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(1));
        }

        if (n.getHijos().get(0).getValue().tipo == TipoToken.NUMBER
                && n.getHijos().get(1).getValue().tipo == TipoToken.NUMBER) {
            n.getValue().tipo = TipoToken.NUMBER;
            n.getValue().lexema = ""
                    + (Double.parseDouble(n.getHijos().get(0).getValue().lexema)
                            / Double.parseDouble(n.getHijos().get(1).getValue().lexema));
        } else {
            System.out.println(
                    "Division de " + n.getHijos().get(0).getValue().tipo + " "
                            + n.getHijos().get(1).getValue().tipo
                            + " no permitida");
            System.exit(0);
        }
    }

    private void resolverMultiplicacion(Nodo n) {
        if (n.getHijos().get(0).getHijos() != null) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getHijos() != null) {
            resolver(n.getHijos().get(1));
        }
        if (n.getHijos().get(0).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(0));
        }
        if (n.getHijos().get(1).getValue().tipo == TipoToken.ID) {
            resolver(n.getHijos().get(1));
        }

        if (n.getHijos().get(0).getValue().tipo == TipoToken.NUMBER
                && n.getHijos().get(1).getValue().tipo == TipoToken.NUMBER) {
            n.getValue().tipo = TipoToken.NUMBER;
            n.getValue().lexema = ""
                    + (Double.parseDouble(n.getHijos().get(0).getValue().lexema)
                            * Double.parseDouble(n.getHijos().get(1).getValue().lexema));
        } else {
            System.out.println(
                    "Multiplicacion de " + n.getHijos().get(0).getValue().tipo + " "
                            + n.getHijos().get(1).getValue().tipo
                            + " no permitida");
            System.exit(0);
        }
    }
}
