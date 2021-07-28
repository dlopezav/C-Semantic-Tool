#include <iostream>

int main(){
    //-- Casos de overflows

    // Cuando el valor de una constante excede el limite de su representación.
    // - limite de enteros de 32 bits.
    long long overflow_1 = 2147483648;
    long long overflow_2 = -2147483649;

    // - limite de enteros de 64 bits.
    long long overflow_3 = 9223372036854775808LL;
    long long overflow_4 = -9223372036854775809LL;

    // - funciona con varios sistemas numéricos.
    int overflow_5 = 0xfffffffff; // Tiene una F de más
    int overflow_6 = 0b111111111111111111111111111111111; // Tiene un 1 de más

    // - El producto de valores excede el limte que representa su producto.
    long long overflow_7 = 2147483647 * 2;
    long long overflow_8 = 2147483647 * 2LL; 

    // - Cuando la suma de valores excede el limite que representa su producto.
    long long overflow_8 = 2147483645 + 1 + 1 + 1 + 1 + 1;

    // - Se tienen en cuenta casting de datos.
    long long overflow_10 = ((long long) 2147483647) * 2147483647;

    // - Se usan variables personalizadas.
    int overflow_11 = 2147483640 + A;


    //-- Casos de casting.
    // - Alerta de división entera.
    int casting_1 = 2 / 3;

    // - Alerta en producto grande.
    int casting_2 = 2 * 4 * 5 * 2147483647;

    // - Alerta en producto grande.
    int casting_3 = 2 * B * 5 * 10;

    // - Alerta de módulo con decimales.
    int casting_4 = 4.0 % 5.0;

    // - Alerta en asignaciones.
    short casting_5 = 1f;
    int casting_6 = 1.0f;    
    double casting_7 = 100;
    float casting_8 = 10;
    long int casting_9 = 50000f;
    long long casting_10 = 100.0;

    // Dirección en flujos de entrada y salida
    std::cin << casting_11; // Dirección erronea
    std::cin >> casting_11;
    std::cout >> casting_11; // Dirección erronea
    std::cout << casting_11;

    // Generación de cores en arreglos.
    int arreglo[12];
    arreglo[15] = 10;

    return 0;

} 
