    package com.cursodeandroid.flappybird;

    import com.badlogic.gdx.ApplicationAdapter;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.graphics.Color;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.OrthographicCamera;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.BitmapFont;
    import com.badlogic.gdx.graphics.g2d.Sprite;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
    import com.badlogic.gdx.math.Circle;
    import com.badlogic.gdx.math.Intersector;
    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.utils.viewport.StretchViewport;
    import com.badlogic.gdx.utils.viewport.Viewport;

    import java.util.Random;


    public class FlappyBird extends ApplicationAdapter {

        private SpriteBatch batch;
        private Texture[] passaros;
        private Texture planoFundo;

        private Texture canoTopo;
        private Texture canoBaixo;
        private Texture gameOver;

        private Random numeroRandomico;
        private BitmapFont fonte;
        private BitmapFont mensagem;

        private float larguraDispositivo;
        private float alturaDispositivo;
        private int alturaEntreCanosRandomica;
        private int estadoJogo=0;// 0--> jogo não iniciado; 1--> jogo iniciado; 2--> jogo gameover
        private int pontuacao=0;

        private float variacao=0;
        private float velocidadeQueda=0;
        private float posicaoInicialVertical;
        private float posicaoMovimentoCanoHorizontal;
        private float espacoEntreCanos;
        private float deltaTime;
        private boolean marcouPonto;

        private Circle passaroCirculo;
        private Rectangle canoTopoRetangulo;
        private Rectangle canoBaixoRetangulo;
        private ShapeRenderer shapeRenderer;

        //Câmera
        private OrthographicCamera camera;
        private Viewport viewport;
        private final float VIRTUAL_WIDTH = 768;
        private final float VIRTUAL_HEIGHT = 1024;

        private int rotacaoPassaro = 0;
        private Sprite sprite;


        @Override
        public void create () {
            // Inicialização dos atributos
            batch = new SpriteBatch();
            passaros = new Texture[3];
            passaros[0] = new Texture("passaro1.png");
            passaros[1] = new Texture("passaro2.png");
            passaros[2] = new Texture("passaro3.png");
            planoFundo = new Texture("fundo.png");
            canoTopo = new Texture("cano_topo.png");
            canoBaixo = new Texture("cano_baixo.png");
            gameOver = new Texture("game_over.png");
            passaroCirculo = new Circle();
            shapeRenderer = new ShapeRenderer();

            fonte = new BitmapFont();
            fonte.setColor(Color.WHITE);
            fonte.getData().setScale(6);

            mensagem = new BitmapFont();
            mensagem.setColor(Color.WHITE);
            mensagem.getData().setScale(3);

            larguraDispositivo = VIRTUAL_WIDTH;
            alturaDispositivo = VIRTUAL_HEIGHT;

            posicaoInicialVertical = alturaDispositivo / 2;
            posicaoMovimentoCanoHorizontal = larguraDispositivo;
            espacoEntreCanos = 300;

            numeroRandomico = new Random();

            //Configurações da câmera
            camera = new OrthographicCamera();
            camera.position.set(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2, 0);
            viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        }

        @Override
        public void render () {

            camera.update();

            //Limpar frames anteriores
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

            deltaTime = Gdx.graphics.getDeltaTime();
            variacao += deltaTime * 10;
            if (variacao > passaros.length) variacao = 0;

            if (estadoJogo == 0){
                if (Gdx.input.justTouched()){
                    estadoJogo = 1;
                }
            } else {

                velocidadeQueda++;
                if (posicaoInicialVertical > 0 || velocidadeQueda < 0) {
                    posicaoInicialVertical -= velocidadeQueda;
                }

                if (estadoJogo == 1){
                    posicaoMovimentoCanoHorizontal -= deltaTime * 150;

                    // Captura o toque na tela
                    if (Gdx.input.justTouched()) {
                        velocidadeQueda = -15;
                    }

                    // Verifica se o cano saiu inteiramente da tela
                    if (posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()) {
                        posicaoMovimentoCanoHorizontal = larguraDispositivo;
                        alturaEntreCanosRandomica = numeroRandomico.nextInt(400) - 200;
                        marcouPonto = false;
                    }

                    if (posicaoMovimentoCanoHorizontal < 100){
                        if (!marcouPonto){
                            pontuacao++;
                            marcouPonto = true;
                        }
                    }

                } else {// tela de gameOver
                    rotacaoPassaro = -90;
                    if (Gdx.input.justTouched()){
                        marcouPonto = false;
                        estadoJogo = 0;
                        pontuacao = 0;
                        velocidadeQueda = 0;
                        posicaoInicialVertical = alturaDispositivo / 2;
                        posicaoMovimentoCanoHorizontal = larguraDispositivo;
                        rotacaoPassaro = 0;
                    }

                }
            }

            //Configurar dados de projeção da câmera
            batch.setProjectionMatrix(camera.combined);

            batch.begin();

            // Desenha o plano de fundo do jogo
            batch.draw(planoFundo, 0, 0, larguraDispositivo, alturaDispositivo);

            batch.draw(canoTopo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandomica);
            batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, (alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica));
            // Desenha o pássaro voando na tela
            //batch.draw(passaros[(int) variacao], 100, posicaoInicialVertical);

            sprite = new Sprite(passaros[(int) variacao]);
            sprite.rotate(rotacaoPassaro);
            sprite.setPosition(100, posicaoInicialVertical);
            sprite.draw(batch);

            fonte.draw(batch, String.valueOf(pontuacao), larguraDispositivo / 2, alturaDispositivo - 50);

            if (estadoJogo == 2){
                batch.draw(gameOver, larguraDispositivo / 2 - gameOver.getWidth()/2, alturaDispositivo /2);
                mensagem.draw(batch, "Toque para reiniciar!", larguraDispositivo /2 - 200, alturaDispositivo / 2 - gameOver.getHeight() / 2);

            }

            batch.end();

            passaroCirculo.set(100 + passaros[0].getWidth() / 2, posicaoInicialVertical + passaros[0].getHeight() / 2, passaros[0].getWidth() / 2);
            canoBaixoRetangulo = new Rectangle(
                    posicaoMovimentoCanoHorizontal, (alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica),
                    canoBaixo.getWidth(), canoBaixo.getHeight()
            );
            canoTopoRetangulo = new Rectangle(
                    posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandomica,
                    canoTopo.getWidth(), canoTopo.getHeight()
            );

            // Desenhar formas
           /** shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);// Shape do tipo preenchido
            shapeRenderer.circle(passaroCirculo.x, passaroCirculo.y, passaroCirculo.radius);
            shapeRenderer.rect(canoBaixoRetangulo.getX(), canoBaixoRetangulo.getY(), canoBaixoRetangulo.getWidth(), canoBaixoRetangulo.getHeight());
            shapeRenderer.rect(canoTopoRetangulo.getX(), canoTopoRetangulo.getY(), canoTopoRetangulo.getWidth(), canoTopoRetangulo.getHeight());
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.end();**/

            // Testando as colisões
            if(Intersector.overlaps(passaroCirculo, canoTopoRetangulo) || Intersector.overlaps(passaroCirculo, canoBaixoRetangulo)
                    || posicaoInicialVertical <= 0 || posicaoInicialVertical >= alturaDispositivo){
                Gdx.app.log("Colisao", "Houve colisao!");
                estadoJogo = 2;
            }

        }

        @Override
        public void resize(int width, int height) {
            viewport.update(width, height);
        }
    }
